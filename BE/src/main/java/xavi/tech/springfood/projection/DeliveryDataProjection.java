package xavi.tech.springfood.projection;

import org.springframework.beans.factory.annotation.Value;

public interface DeliveryDataProjection {
	
	long getId();
	@Value("#{'(' + target.city + ') ' + target.address}")
	String getAddress();
	boolean getMain();
	String getPhone();

}
