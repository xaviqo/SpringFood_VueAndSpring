package xavi.tech.springfood.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xavi.tech.springfood.projection.DashboardOrdersProjection;
import xavi.tech.springfood.utils.Utils;

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
			dto.setTimestamp(Utils.timestampToHMString(projection.getTimestamp()));
			dto.setClientAddress(projection.getClientAddress());
			dto.setTotalAmount(Utils.roundTotalAmount(projection.getTotalAmount())+"€");
			dto.setClientName(projection.getClientName());
			dto.setClientPhone(projection.getClientPhone());
			dto.setDelivered(projection.getDelivered());
			dto.setPaid(projection.getPaid());
		} catch (Exception e) {
			//TODO: LOG HERE
			System.out.println(e);
			return null;
		}
		
		return dto;
	}
	
}
