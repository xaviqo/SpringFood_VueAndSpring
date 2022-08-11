package xavi.tech.springfood.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;
import xavi.tech.springfood.projection.DashboardOrderLinesProjection;
import xavi.tech.springfood.utils.Utils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineDashboardDTO {
	
	String idAndDashboardId;
	String productIdAndName;
	String productQuantity;
	String lineTotal;
	
	public static OrderLineDashboardDTO projectionToDTO(DashboardOrderLinesProjection projection,int boardId) {
		
		OrderLineDashboardDTO dto = new OrderLineDashboardDTO();
		
		try {
			
			dto.setIdAndDashboardId(boardId+" - L"+projection.getLineId());
			dto.setProductIdAndName("P"+projection.getProductIdName());
			dto.setProductQuantity(projection.getQuantity()+" uds.");
			dto.setLineTotal(Utils.roundTotalAmount(projection.getTotalLine())+"€");
			
		} catch (Exception e){
			throw new SpringFoodException(SpringFoodError.InternalError,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return dto;
	}

}
