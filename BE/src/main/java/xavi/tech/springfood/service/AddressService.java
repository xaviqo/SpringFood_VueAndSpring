package xavi.tech.springfood.service;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;
import xavi.tech.springfood.model.Account;
import xavi.tech.springfood.model.Address;
import xavi.tech.springfood.model.Client;
import xavi.tech.springfood.projection.DeliveryDataProjection;
import xavi.tech.springfood.repository.AccountRepository;
import xavi.tech.springfood.repository.AddressRepository;

@AllArgsConstructor
@Service
@Transactional
public class AddressService {
	
	private final AddressRepository addressRepository;
	private final AccountRepository accountRepository;
	
	public ResponseEntity<?> getClientAddress(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String clientEmail = authentication.getPrincipal().toString();
		List<DeliveryDataProjection> deliveryData = addressRepository.getDeliveryDataByEmail(clientEmail);
		
		if (Objects.nonNull(deliveryData)) {
			return new ResponseEntity<List<DeliveryDataProjection>>(deliveryData,HttpStatus.OK);
		}
		throw new SpringFoodException(SpringFoodError.ErrorRetrievingAddres,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<?> newAddress(Address address){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		try {
			if (address.getCity().isBlank()) {
				throw new SpringFoodException(SpringFoodError.ErrorCreatingAddress,"City cannot be empty",HttpStatus.INTERNAL_SERVER_ERROR);
			} else if (address.getAddress().isBlank()) {
				throw new SpringFoodException(SpringFoodError.ErrorCreatingAddress,"Address cannot be empty",HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			address.setClient((Client)accountRepository.findByEmail(authentication.getPrincipal().toString()));
			return new ResponseEntity<Address>(addressRepository.save(address),HttpStatus.OK);

		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.ErrorCreatingAddress,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<?> updateToMainAddress(String id){
		
		long parsedId = 0L;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Account account = accountRepository.findByEmail(authentication.getPrincipal().toString());

		
		try {
			parsedId = Long.valueOf(id);		
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.ErrorDeletingAddres,"Invalid address format",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (addressRepository.existsById(parsedId)) {
			
			if (addressRepository.updateMainAddressStatus(account.getUserId()) == 1) {
				return ResponseEntity.ok().body(addressRepository.updateToMain(parsedId));
			}
			
		} else {
			throw new SpringFoodException(SpringFoodError.ErrorDeletingAddres,"Invalid address id",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		throw new SpringFoodException(SpringFoodError.ErrorCreatingAddress,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<?> deleteAddress(String id){
		
		long parsedId = 0L;
		Boolean isMain = null;
		
		try {
			parsedId = Long.valueOf(id);		
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.ErrorDeletingAddres,"Invalid address format",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		isMain = addressRepository.isAddressMain(parsedId);
		
		if (Objects.isNull(isMain)) {
			throw new SpringFoodException(SpringFoodError.ErrorDeletingAddres,"Invalid address id",HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (isMain) {
			throw new SpringFoodException(SpringFoodError.ErrorDeletingAddres,"Cannot delete main address",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseEntity.ok().body(addressRepository.deleteByAddressId(parsedId));
		
	}

}
