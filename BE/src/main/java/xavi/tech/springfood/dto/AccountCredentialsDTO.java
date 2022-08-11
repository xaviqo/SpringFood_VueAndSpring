package xavi.tech.springfood.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xavi.tech.springfood.model.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCredentialsDTO {
	
	private String email;
	private String password;

}
