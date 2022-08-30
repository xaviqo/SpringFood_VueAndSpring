package xavi.tech.springfood.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.service.OrderService;

@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping(path="/api/client/order")
public class ClientOrdersController {
	
	private final OrderService orderService;
	
	@GetMapping(path = "/getOrders")
	public ResponseEntity<?> getClientOrders() {
		return orderService.getClientOrders();
	}

}
