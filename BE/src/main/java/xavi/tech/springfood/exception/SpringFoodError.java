package xavi.tech.springfood.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum SpringFoodError {
	
	/*
	*
	* O - INTERNAL 
	* 1 - ACCOUNTS / 11 - ROLES
	*
	*/
	
	InternalError("000","Internal Springfood Error"),
	InvalidUser("100","User not found or wrong credentials"),
	GeneralLoginError("101","General login error"),
	EmailAlreadyExists("102","This email is already registered"),
	ErrorCreatingClient("110","Error creating client"),
	ErrorCreatingWorker("120","Error creaing worker"),
	InvalidRole("130","Account role is not valid"),
	InvalidJWToken("140","Invalid token"),
	InvalidJWTokenFormat("141","Invalid token format"),
	ExpiredJWToken("142","The token has expired");

	private String code;
	private String description;
	
		
}
