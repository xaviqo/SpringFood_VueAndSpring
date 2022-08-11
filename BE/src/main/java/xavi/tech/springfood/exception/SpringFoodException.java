package xavi.tech.springfood.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpringFoodException extends RuntimeException{

	private static final long serialVersionUID = 5985330073774045309L;

	private SpringFoodError errorCode;
	private HttpStatus httpStatus;
	
	public SpringFoodException(SpringFoodError errorCode, HttpStatus httpStatus) {
		super();
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
	}

}
