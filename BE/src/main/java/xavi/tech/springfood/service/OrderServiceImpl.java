package xavi.tech.springfood.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import xavi.tech.springfood.dto.OrderDashboardDTO;
import xavi.tech.springfood.dto.OrderLineDashboardDTO;
import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;
import xavi.tech.springfood.projection.DashboardOrderLinesProjection;
import xavi.tech.springfood.projection.DashboardOrdersProjection;
import xavi.tech.springfood.repository.OrderLineRepository;
import xavi.tech.springfood.repository.OrderRepository;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final OrderLineRepository lineRepository;

	public ResponseEntity<?> getTodayOrders() {

		List<OrderDashboardDTO> orderDashboardList = new ArrayList<>();
		DateFormat formatPrefix = new SimpleDateFormat("yyMMdd");
		String todayOrderPrefix = String.format(formatPrefix.format(new Date()));

		try {

			for (DashboardOrdersProjection projection : orderRepository.getTodayOrders(todayOrderPrefix)) {
				orderDashboardList.add(OrderDashboardDTO.projectionToDTO(projection));
			}

			return ResponseEntity.ok().body(orderDashboardList);

		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.InternalError,HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<?> getAllOrders() {

		List<OrderDashboardDTO> allOrdersDTO = new ArrayList<>();

		try {

			orderRepository.getAllOrders().forEach(
					order -> allOrdersDTO.add(
							OrderDashboardDTO.projectionToDTO(order))
					);

			return ResponseEntity.ok().body(allOrdersDTO);

		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.InternalError,HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@Override
	public ResponseEntity<?> getLinesFromOrder(String orderId) {
		
		List<OrderLineDashboardDTO> orderDashboardList = new ArrayList<>();
		int boardLine = 0;
		
		try {
			
			for (DashboardOrderLinesProjection projection : lineRepository.findByOrderOrderId(orderId)) {
				boardLine++;
				orderDashboardList.add(OrderLineDashboardDTO.projectionToDTO(projection,boardLine));
			}
			
			return ResponseEntity.ok().body(orderDashboardList);
			
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.InternalError,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

	}

}
