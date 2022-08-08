package xavi.tech.springfood.service;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import xavi.tech.springfood.dto.OrdersDTO;
import xavi.tech.springfood.repository.OrderRepository;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	public ResponseEntity<?> getTodayOrders() {

		List<OrdersDTO> todayOrdersDTO = new ArrayList<>();
		DateFormat formatPrefix = new SimpleDateFormat("yyMMdd");
		String todayOrderPrefix = String.format(formatPrefix.format(new Date()));

		try {

			orderRepository.getTodayOrders(todayOrderPrefix).forEach(
					order -> todayOrdersDTO.add(
							OrdersDTO.projectionToDTO(order))
					);

			return new ResponseEntity<>(todayOrdersDTO,HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<?> getAllOrders() {

		List<OrdersDTO> allOrdersDTO = new ArrayList<>();

		try {

			orderRepository.getAllOrders().forEach(
					order -> allOrdersDTO.add(
							OrdersDTO.projectionToDTO(order))
					);

			return new ResponseEntity<>(allOrdersDTO,HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}

	}

}
