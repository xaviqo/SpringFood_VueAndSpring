package xavi.tech.springfood.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:1337", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(path="/api/admin/order")
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
