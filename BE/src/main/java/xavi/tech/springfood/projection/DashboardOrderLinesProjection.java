package xavi.tech.springfood.projection;

import org.springframework.beans.factory.annotation.Value;

public interface DashboardOrderLinesProjection {

	long getLineId();
	@Value("#{ + target.product.productId + ' - ' + target.product.name}")
	String getProductIdName();
	long getQuantity();
	double getTotalLine();
	
}
