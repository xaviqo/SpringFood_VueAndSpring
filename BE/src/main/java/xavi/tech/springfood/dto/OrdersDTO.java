package xavi.tech.springfood.dto;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xavi.tech.springfood.projection.dasboardOrdersProjection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {

	String orderId;
	String timestamp;
	String clientAddress;
	Double totalAmount;
	String clientName;
	String clientPhone;
	boolean paid;
	boolean delivered;
	
	public static OrdersDTO projectionToDTO(dasboardOrdersProjection projection) {
		OrdersDTO dto = new OrdersDTO();
		
		try {
			dto.setOrderId(projection.getOrderId());
			dto.setTimestamp(timestampToHMString(projection.getTimestamp()));
			dto.setClientAddress(projection.getClientAddress());
			dto.setTotalAmount(roundTotalAmount(projection.getTotalAmount()));
			dto.setClientName(projection.getClientName());
			dto.setClientPhone(projection.getClientPhone());
			dto.setDelivered(projection.getDelivered());
			dto.setPaid(projection.getPaid());
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
		return dto;
	}
	
	public static String timestampToHMString(Timestamp timestamp) {
		return new SimpleDateFormat( "HH:mm" )
				.format(timestamp);
	}
	
	public static Double roundTotalAmount(Double totalAmount) {
		DecimalFormat df = new DecimalFormat("#.##");  
		return Double.valueOf(df.format(totalAmount));
	}
	
}
