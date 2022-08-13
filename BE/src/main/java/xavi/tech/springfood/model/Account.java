package xavi.tech.springfood.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xavi.tech.springfood.Globals;
import xavi.tech.springfood.utils.IdGenerator;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Account implements UserDetails{

	private static final long serialVersionUID = 5634953997155892981L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_seq")
	@GenericGenerator(
			name = "acc_seq", 
			strategy = "xavi.tech.springfood.utils.IdGenerator", 
			parameters = {
					@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "50")})
	private String userId;

	@Column(length = 40, nullable = false)
	private String name;
	@Column(length = 12, nullable = false)
	private String phone;
	@Column(length = 40, nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column
	private Role role;
	@Transient
	private Map<String,Boolean> navBar;

	public Account(String name, String phone, String email, String password, Role role) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.role = role;
		this.navBar = Globals.getClientNavBar();
	}
	
	public Map<String,Boolean> getNavBar() {
		return Globals.getClientNavBar();
	}

	public Account(Role role) {
		this.role = role;
	}

	// INTERFACE METHODS --> UserDetails //

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Authority> authorities = new HashSet<>();
		authorities.add(new Authority(this.role.getDescription()));
		return authorities;
	}

	@Override
	public String getUsername() {
		return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}
