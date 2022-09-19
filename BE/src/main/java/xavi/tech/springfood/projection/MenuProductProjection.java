package xavi.tech.springfood.projection;

public interface MenuProductProjection {

	String getProductId();
	String getName();
	String getDescription();
	int getPrice();
	String getCloudId();
	String getType();
	boolean isActive();
	
}
