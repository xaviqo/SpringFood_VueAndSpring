package xavi.tech.springfood.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.stripe.model.Charge;

import lombok.RequiredArgsConstructor;
import xavi.tech.springfood.dto.OrderDashboardDTO;
import xavi.tech.springfood.dto.OrderForClientOrdersDTO;
import xavi.tech.springfood.dto.StripeDTO;
import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;
import xavi.tech.springfood.model.Address;
import xavi.tech.springfood.model.Client;
import xavi.tech.springfood.model.Order;
import xavi.tech.springfood.model.OrderLine;
import xavi.tech.springfood.model.SpringFoodCfg;
import xavi.tech.springfood.projection.DashboardOrdersProjection;
import xavi.tech.springfood.repository.ClientRepository;
import xavi.tech.springfood.repository.OrderLineRepository;
import xavi.tech.springfood.repository.OrderRepository;
import xavi.tech.springfood.repository.SfCfgRepository;

@RequiredArgsConstructor
@Service
public class OrderService{

	private final OrderRepository orderRepository;
	private final OrderLineRepository lineRepository;
	private final ClientRepository clientRepository;
	private final SfCfgRepository cfgRepository;

	public Order createOrder(StripeDTO stripeDto) {

		//TODO FALTA GESTIONAR WORKER DE CADA PEDIDO

		Order order = new Order();
		order.setClient(clientRepository.findByEmail(stripeDto.getEmail()));
		order.setDeliveryAddress(new Address(stripeDto.getAddressId()));
		order.setDeliveryTime(stripeDto.getDeliveryTime());
		order = orderRepository.save(order);

		for (OrderLine line : stripeDto.getOrderLines()) {
			line.setOrder(order);
			order.setOrderLine(line);
			lineRepository.save(line);
		}

		return order;
	}

	public ResponseEntity<?> getTodayOrders() {

		List<OrderDashboardDTO> orderDashboardList = new ArrayList<>();
		Optional<SpringFoodCfg> cfg = cfgRepository.findById(1);
		
		try {
							
				LocalDateTime open = LocalDateTime.now();
				open.with(cfg.get().getOpenDashboardTime());
				LocalDateTime close = open.plusDays(cfg.get().getDashboardTableDaysDifference());
				close.with(cfg.get().getCloseDashboardTime());

			for (DashboardOrdersProjection projection : orderRepository.getTodayOrders(open,close)) {
				orderDashboardList.add(OrderDashboardDTO.projectionToDTO(projection));
			}

			return ResponseEntity.ok().body(orderDashboardList);

		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.InternalError,HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	public ResponseEntity<?> getClientOrders() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Client client = clientRepository.findByEmail(authentication.getName());
		List<Order> allClientOrders = orderRepository.findByClient(client);
		
		return ResponseEntity.ok(OrderForClientOrdersDTO.orderListToDtoList(allClientOrders));

	}
	
	public ResponseEntity<?> getLinesFromOrder(String orderId) {


		try {
			return ResponseEntity.ok().body(lineRepository.orderLinesDashboard(orderId));
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.InternalError,HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}

	public ResponseEntity<?> getDeliveredPercentCard(boolean today) {

		long remaining = 0L;
		long delivered = 0L;
		Map<String,Long> cardInfo = new HashMap<String, Long>();

		if (today) {
			remaining = orderRepository.deliveredPercentCard(LocalDate.now(), false);
			delivered = orderRepository.deliveredPercentCard(LocalDate.now(), true);
		} else {
			remaining = orderRepository.deliveredPercentCard(LocalDate.now().minusDays(1), false);
			delivered = orderRepository.deliveredPercentCard(LocalDate.now().minusDays(1), true);
		}

		cardInfo.put("remain", remaining);
		cardInfo.put("delivered", delivered);

		return ResponseEntity.ok().body(cardInfo);
	}

	public ResponseEntity<?> getLastOrderCard(boolean delivered) {

		if (delivered) {
			return ResponseEntity.ok().body(orderRepository.getLastDeliveredOrder());
		} else {
			return ResponseEntity.ok().body(orderRepository.getLastOrder());
		}

	}

	public ResponseEntity<?> getEarningBarsCard() {

		TreeMap<DayOfWeek,Long> infoForEachBar = new TreeMap<>();
		LocalDateTime from = null;
		LocalDateTime to = null;
		long total = 0L;

		for (int i = LocalDate.now().getDayOfWeek().getValue(); i > 0; i--) {
				
			from = LocalDate.now().minusDays(i).atStartOfDay();
			to = from.plusDays(1);
			
			for (long amount : orderRepository.totalAmountsOfDay(from,to)) total+= amount;
			
			infoForEachBar.put(from.getDayOfWeek(),total);
			
			total = 0L;

		}
		
		for (long amount : orderRepository.totalAmountsOfDay(
				LocalDate.now().atStartOfDay(),LocalDate.now().plusDays(1).atStartOfDay()
				)) total += amount;

		infoForEachBar.put(LocalDate.now().getDayOfWeek(), total);

		return ResponseEntity.ok().body(infoForEachBar);

	}
	
	public ResponseEntity<?> getDemandProductCard(boolean high) {

		if (high) return ResponseEntity.ok().body(orderRepository.highProductDemand());

		return ResponseEntity.ok().body(orderRepository.lowProductDemand());

	}

	public void updateToPaid(Order order, Charge stripeCharge) {
		order.setStripeId(stripeCharge.getId());
		orderRepository.updateToPaid(order.getOrderId());
	}

}
