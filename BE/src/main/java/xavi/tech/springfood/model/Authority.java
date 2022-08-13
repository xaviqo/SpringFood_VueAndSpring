package xavi.tech.springfood.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Authority implements GrantedAuthority {
	
	private static final long serialVersionUID = 4830596919664866627L;
	
	private String authority;

	@Override
	public String getAuthority() {
		return this.authority;
	}

}