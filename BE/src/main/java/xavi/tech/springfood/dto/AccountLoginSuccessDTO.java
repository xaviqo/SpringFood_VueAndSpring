package xavi.tech.springfood.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class AccountLoginSuccessDTO {
	
	String token;
	String email;
	String role;
	LocalDateTime dateTime;

}
