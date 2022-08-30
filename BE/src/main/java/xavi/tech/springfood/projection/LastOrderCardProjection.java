package xavi.tech.springfood.projection;

import java.time.LocalDateTime;

public interface LastOrderCardProjection {

	String getClient();
	String getClientPhone();
	String getResponsible();
	LocalDateTime getOrderDate();
	
}
