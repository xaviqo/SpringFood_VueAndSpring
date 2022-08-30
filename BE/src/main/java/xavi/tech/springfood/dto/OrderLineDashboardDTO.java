package xavi.tech.springfood.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;
import xavi.tech.springfood.projection.DashboardOrderLinesProjection;
import xavi.tech.springfood.utils.SFUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineDashboardDTO {
	
	String dashboardId;
	String internalId;
	String productId;
	String productName;
	String productQuantity;
	String lineTotal;
	
	public static OrderLineDashboardDTO projectionToDTO(DashboardOrderLinesProjection projection,int boardLine) {
		
		OrderLineDashboardDTO dto = new OrderLineDashboardDTO();
		
		try {
			
			dto.setDashboardId(String.valueOf(boardLine));
			dto.setInternalId(String.valueOf(projection.getLineId()));
			dto.setProductId("P_"+String.valueOf(projection.getProductId()));
			dto.setProductName(projection.getProductName());
			dto.setProductQuantity(projection.getQuantity()+" uds.");
			dto.setLineTotal(projection.getTotalLine()+"€");
			
		} catch (Exception e){
			throw new SpringFoodException(SpringFoodError.InternalError,"Error creating OrderLineDashboardDTO",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return dto;
	}

}
