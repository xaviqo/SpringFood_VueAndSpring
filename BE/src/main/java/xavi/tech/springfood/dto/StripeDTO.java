package xavi.tech.springfood.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xavi.tech.springfood.model.OrderLine;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StripeDTO {

	private String orderId;
	private String token;
	private String cardToken;
	private Map<String,Integer> productsAndQuantity;
	@JsonIgnore
	private List<OrderLine> orderLines;
	@JsonIgnore 
	private String email;
	private int addressId;
	private String deliveryTime;
	private long clientTotal;

}
