package xavi.tech.springfood.projection;

public interface DashboardOrderLinesProjection {

	long getLineId();
	String getProductId();
	String getProductName();
	long getQuantity();
	double getTotalLine();
	
}
