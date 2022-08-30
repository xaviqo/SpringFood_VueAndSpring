package xavi.tech.springfood.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StripePaymentDTO {
	
	//Mine
	private Map<String,Integer> productsAndQuantity;
	private long clientTotal;
	private String token;
	
	//Stripe
	private long amount;

}
