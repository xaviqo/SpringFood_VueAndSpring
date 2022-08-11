package xavi.tech.springfood.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(value = SpringFoodException.class)
	public ResponseEntity<Map> apiExceptionHandler(SpringFoodException e){
		
		Map<String, String> error = new HashMap<>();
		error.put("code", e.getErrorCode().getCode());
		error.put("message", e.getErrorCode().getDescription());
		error.put("timestamp", LocalDateTime.now().toString());
		
		return new ResponseEntity<Map>(error, e.getHttpStatus());
	}
	
}
