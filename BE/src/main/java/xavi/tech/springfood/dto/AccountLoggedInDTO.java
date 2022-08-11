package xavi.tech.springfood.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;
import xavi.tech.springfood.model.Account;
import xavi.tech.springfood.model.Role;
import xavi.tech.springfood.utils.Utils;

@Getter
@Setter
@NoArgsConstructor
public class AccountLoggedInDTO {
	
	String email;
	String token;
	Role role;
	
	public static AccountLoggedInDTO accountToDTO(Account account, String token) {
		
		AccountLoggedInDTO dto = new AccountLoggedInDTO();
		
		try {
			dto.setEmail(account.getEmail());
			dto.setRole(account.getRole());
			dto.setToken(token);
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.InternalError,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return dto;
	}

}
