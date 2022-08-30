package xavi.tech.springfood.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.Globals;
import xavi.tech.springfood.dto.StripeDTO;
import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;
import xavi.tech.springfood.model.Order;
import xavi.tech.springfood.model.OrderLine;
import xavi.tech.springfood.model.Product;
import xavi.tech.springfood.repository.OrderRepository;
import xavi.tech.springfood.repository.ProductRepository;

@AllArgsConstructor
@Service
public class StripeService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final OrderService orderService;

	public Charge charge(StripeDTO payment) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication instanceof AnonymousAuthenticationToken) {
			throw new SpringFoodException(SpringFoodError.InvalidJWToken,HttpStatus.UNAUTHORIZED);
		}

		payment.setOrderLines(createLinesAndCheckTotal(payment));

		if (payment.getOrderLines() != null && payment.getOrderLines().size()>0) {

			Order order = new Order();

			payment.setEmail(authentication.getName());

			if (orderRepository.existsById(payment.getOrderId())) {
				order.setOrderId(payment.getOrderId());
			} else if (payment.getOrderId().isBlank()){
				order = orderService.createOrder(payment);
			} else {
				throw new SpringFoodException(SpringFoodError.InternalError,HttpStatus.BAD_REQUEST);
			}

			Stripe.apiKey = Globals.STRIPE_API_SEC;
			Map<String, Object> stripeParams = new HashMap<>();
			Charge charge = null;

			stripeParams.put("receipt_email", payment.getEmail());
			stripeParams.put("amount", payment.getClientTotal());
			stripeParams.put("currency", "eur");
			stripeParams.put("description",order.getOrderId());
			stripeParams.put("source", payment.getToken());

			try {
				charge = Charge.create(stripeParams);
			} catch (CardException ce) {

				if("incorrect_number".equals(ce.getCode())) {
					throw new SpringFoodException(SpringFoodError.StripeCardIncorrectNumber,order.getOrderId(),HttpStatus.BAD_REQUEST);
				} else if("incorrect_cvc".equals(ce.getCode())) {
					throw new SpringFoodException(SpringFoodError.StripeCardIncorrectCVC,order.getOrderId(),HttpStatus.BAD_REQUEST);
				} else if("insufficient_funds".equals(ce.getDeclineCode())) {
					throw new SpringFoodException(SpringFoodError.StripeCardInsufficientFunds,order.getOrderId(),HttpStatus.BAD_REQUEST);
				} else if("expired_card".equals(ce.getCode())) {
					throw new SpringFoodException(SpringFoodError.StripeCardExpired,order.getOrderId(),HttpStatus.BAD_REQUEST);
				} else {
					throw new SpringFoodException(SpringFoodError.StripeCardCallIssuer,order.getOrderId(),HttpStatus.BAD_REQUEST);
				}

			} catch (StripeException se) {
				throw new SpringFoodException(SpringFoodError.StripeError,order.getOrderId(),HttpStatus.BAD_REQUEST);
			}

			orderService.updateToPaid(order, charge);
			return charge;

		}

		throw new SpringFoodException(SpringFoodError.OrderPriceAlteration,HttpStatus.BAD_REQUEST);
	}


	private List<OrderLine> createLinesAndCheckTotal(StripeDTO payment) {

		long calculatedTotal = 0L;
		List<OrderLine> orderLines = new ArrayList<>();

		try {
			for (Map.Entry<String,Integer> line : payment.getProductsAndQuantity().entrySet()) {

				long productPrice = productRepository.findPriceByProductId(Long.parseLong(line.getKey()));
				calculatedTotal += productPrice*line.getValue();
				Product product = new Product(Long.parseLong(line.getKey()),productPrice);
				orderLines.add(new OrderLine(product,line.getValue(),productPrice*line.getValue()));

			}
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.OrderPriceAlteration,HttpStatus.BAD_REQUEST);
		}

		if (calculatedTotal == payment.getClientTotal()) {
			return orderLines;
		}

		return null;

	}






}
