package xavi.tech.springfood.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xavi.tech.springfood.utils.IdGenerator;

@Entity
@Table
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
	
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
    @Column(length = 40, nullable = false)
	private String password;
    @Column(nullable = false)
	private Role role;
    
	public Account(String name, String phone, String email, String password, Role role) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Account(Role role) {
		this.role = role;
	}
    


}
