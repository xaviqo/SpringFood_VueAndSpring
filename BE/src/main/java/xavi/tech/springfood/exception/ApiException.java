package xavi.tech.springfood.exception;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiException {

	private String code;
	private LocalDate dateTime;
	private String message;

}

