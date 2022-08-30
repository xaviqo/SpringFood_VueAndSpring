package xavi.tech.springfood.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;
import xavi.tech.springfood.projection.DashboardOrdersProjection;
import xavi.tech.springfood.utils.SFUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDashboardDTO {

	String orderId;
	String timestamp;
	String clientAddress;
	String totalAmount;
	String clientName;
	String clientPhone;
	boolean paid;
	boolean delivered;
	
	public static OrderDashboardDTO projectionToDTO(DashboardOrdersProjection projection) {
		
		OrderDashboardDTO dto = new OrderDashboardDTO();
		
		try {
			dto.setOrderId(projection.getOrderId());
			dto.setTimestamp(SFUtils.timestampToHMString(projection.getTimestamp()));
			dto.setClientAddress(projection.getClientAddress());
			dto.setTotalAmount(String.valueOf(projection.getTotalAmount()));
			dto.setClientName(projection.getClientName());
			dto.setClientPhone(projection.getClientPhone());
			dto.setDelivered(projection.getDelivered());
			dto.setPaid(projection.getPaid());
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.InternalError,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return dto;
	}
	
}
