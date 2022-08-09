package xavi.tech.springfood.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path="/api/order")
public interface OrderController {

	@RequestMapping(path="/getTodayOrders")
	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getTodayOrders();
	
	@RequestMapping(path="/getAllOrders")
	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getAllOrders();
	
	@RequestMapping(path = "/getLinesFromOrder/{orderId}")
	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getLinesFromOrder(@PathVariable String orderId);
}
