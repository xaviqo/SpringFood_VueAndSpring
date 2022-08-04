package xavi.tech.springfood.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xavi.tech.springfood.model.Role;

@Getter
@Setter
@NoArgsConstructor
public class AccountCredentialsDTO {
	
	private String email;
	private String password;
	private Role role;

}
