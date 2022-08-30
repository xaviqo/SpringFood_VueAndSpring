package xavi.tech.springfood.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import xavi.tech.springfood.service.OrderService;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(path="/api/admin/order")
public class AdminOrderController{
	
	private final OrderService orderService;

	@GetMapping(path="/getTodayOrders")
	public ResponseEntity<?> getTodayOrders() {
		return orderService.getTodayOrders();
	}
	
	@GetMapping(path = "/getLinesFromOrder/{orderId}")
	public ResponseEntity<?> getLinesFromOrder(@PathVariable String orderId) {
		return orderService.getLinesFromOrder(orderId);
	}

}
