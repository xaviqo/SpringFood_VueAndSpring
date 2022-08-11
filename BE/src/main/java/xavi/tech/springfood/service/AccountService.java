package xavi.tech.springfood.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.model.Client;


public interface AccountService {

	HttpStatus createPlaceholders();
	ResponseEntity<?> createClient(Client client);
	ResponseEntity<?> login(AccountCredentialsDTO account);
	
}
