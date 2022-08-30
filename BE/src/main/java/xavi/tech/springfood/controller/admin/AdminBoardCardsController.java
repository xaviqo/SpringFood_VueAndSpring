package xavi.tech.springfood.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import xavi.tech.springfood.service.OrderService;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(path="/api/admin/card")
public class AdminBoardCardsController {

	private final OrderService orderService;

	@GetMapping(path="/getDeliveredPercent/{today}")
	public ResponseEntity<?> getDeliveredPercent(@PathVariable boolean today) {
		return orderService.getDeliveredPercentCard(today);
	}
	
	@GetMapping(path="/getLastReadyOrder/{delivered}")
	public ResponseEntity<?> getLastOrder(@PathVariable boolean delivered) {
		return orderService.getLastOrderCard(delivered);
	}
	
	@GetMapping(path="/getEarningBars/")
	public ResponseEntity<?> getEarningBars() {
		return orderService.getEarningBarsCard();
	}
	
	@GetMapping(path="/getDemandProduct/{high}")
	public ResponseEntity<?> getDemandProduct(@PathVariable boolean high) {
		return orderService.getDemandProductCard(high);
	}
	
}
