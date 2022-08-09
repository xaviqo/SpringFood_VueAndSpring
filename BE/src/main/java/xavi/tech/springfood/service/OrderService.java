package xavi.tech.springfood.service;

import org.springframework.http.ResponseEntity;

public interface OrderService {

	ResponseEntity<?> getTodayOrders();
	
	ResponseEntity<?> getAllOrders();
	
	ResponseEntity<?> getLinesFromOrder(String orderId);
	
}
