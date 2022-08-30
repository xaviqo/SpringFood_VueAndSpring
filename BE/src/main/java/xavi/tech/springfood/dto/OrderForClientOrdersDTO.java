package xavi.tech.springfood.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;
import xavi.tech.springfood.model.Address;
import xavi.tech.springfood.model.Order;
import xavi.tech.springfood.model.OrderLine;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderForClientOrdersDTO {
	
	String orderId;
	String deliveryAddress;
	String deliveryTime;
	long totalAmount;
	boolean delivered;
	boolean paid;
	List<Map<String,Object>> orderLines = new ArrayList<>();
	
	public void setOrderLines(Map<String,Object> line) {
		this.orderLines.add(line);
	}
	
	public static List<OrderForClientOrdersDTO> orderListToDtoList(List<Order> orders){
		
		List<OrderForClientOrdersDTO> dtoList = new ArrayList<>();
				
		try {
			orders.forEach(order -> dtoList.add(orderToDto(order)));
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.InternalError,"Error creating OrderForClientOrdersDTO ArrayList",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return dtoList;
	}
	
	public static OrderForClientOrdersDTO orderToDto(Order order) {
		
		OrderForClientOrdersDTO dto = new OrderForClientOrdersDTO();
		
		try {
			
			dto.setOrderId(order.getOrderId());
			dto.setDeliveryAddress("("+order.getDeliveryAddress().getCity()+") "+order.getDeliveryAddress().getAddress());
			dto.setDeliveryTime(order.getDeliveryTime());
			dto.setDelivered(order.isDelivered());
			dto.setTotalAmount(order.getTotalAmount());
			dto.setPaid(order.isPaid());
			
			for (OrderLine line : order.getOrderLines()) {
				Map<String,Object> dtoLine = new HashMap<>();
				dtoLine.put("product", line.getProduct().getName());
				dtoLine.put("price", line.getProduct().getPrice());
				dtoLine.put("quantity", line.getQuantity());
				dtoLine.put("totalLine", line.getTotalLine());
				dto.setOrderLines(dtoLine);
			}
			
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.InternalError,"Error creating OrderForClientOrdersDTO",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return dto;
		
	}

}
