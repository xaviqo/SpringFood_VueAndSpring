package xavi.tech.springfood.service;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.projection.DeliveryDataProjection;
import xavi.tech.springfood.repository.AddressRepository;

@AllArgsConstructor
@Service
public class AddressService {
	
	private final AddressRepository addressRepository;
	
	public ResponseEntity<?> getClientAddress(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String clientEmail = authentication.getPrincipal().toString();
		List<DeliveryDataProjection> deliveryData = addressRepository.getDeliveryDataByEmail(clientEmail);
		
		if (Objects.nonNull(deliveryData)) {
			return new ResponseEntity<List<DeliveryDataProjection>>(deliveryData,HttpStatus.OK);
		}
		return null;
	}

}
