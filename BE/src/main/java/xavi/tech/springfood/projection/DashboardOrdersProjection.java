package xavi.tech.springfood.projection;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Value;

public interface DashboardOrdersProjection {

	String getOrderId();
	Timestamp getTimestamp();
    @Value("#{target.name}")
	String getClientName();
    @Value("#{target.phone}")
    String getClientPhone();
	@Value("#{'(' + target.city + ') ' + target.address}")
    String getClientAddress();
	Double getTotalAmount();
	boolean getPaid();
	boolean getDelivered();
	
}
