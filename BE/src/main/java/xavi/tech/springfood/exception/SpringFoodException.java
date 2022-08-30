package xavi.tech.springfood.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SpringFoodException extends RuntimeException{

	private static final long serialVersionUID = 5985330073774045309L;

	private SpringFoodError errorCode;
	private HttpStatus httpStatus;
	private String extraInfo;

	public SpringFoodException(SpringFoodError errorCode, HttpStatus httpStatus) {
		super();
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
	}

	public SpringFoodException(SpringFoodError errorCode, String extraInfo, HttpStatus httpStatus) {
		super();
		this.errorCode = errorCode;
		this.extraInfo = extraInfo;
		this.httpStatus = httpStatus;
	}

}
