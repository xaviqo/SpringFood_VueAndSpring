package xavi.tech.springfood.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import xavi.tech.springfood.service.OrderService;

@RequiredArgsConstructor
@RestController
public class OrderControllerImpl implements OrderController{
	
	private final OrderService orderService;

	@Override
	public ResponseEntity<?> getTodayOrders() {
		return orderService.getTodayOrders();
	}

	@Override
	public ResponseEntity<?> getAllOrders() {
		return orderService.getAllOrders();
	}

	@Override
	public ResponseEntity<?> getLinesFromOrder(String orderId) {
		return orderService.getLinesFromOrder(orderId);
	}

}
