package xavi.tech.springfood.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.service.AddressService;

@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping(path="/api/client/address")
public class ClientAddressController {
	
	private final AddressService addressService;

	@GetMapping(path = "/getAddress")
	public ResponseEntity<?> getClientAddress() {
		return addressService.getClientAddress();
	}

}
