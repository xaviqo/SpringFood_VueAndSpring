package xavi.tech.springfood.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.dto.StripeDTO;
import xavi.tech.springfood.service.StripeService;

@AllArgsConstructor
@RestController
@RequestMapping(path="/api/stripe")
public class StripePaymentController {
	
	private final StripeService stripeService;
	

	@PostMapping(path="/charge")
	public ResponseEntity<?> charge(@RequestBody StripeDTO dto) {
		return new ResponseEntity<String>(stripeService.charge(dto).toJson(),HttpStatus.OK);
	}


}
