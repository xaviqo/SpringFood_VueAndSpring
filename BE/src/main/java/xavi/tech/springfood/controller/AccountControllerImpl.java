package xavi.tech.springfood.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.model.Client;
import xavi.tech.springfood.service.AccountService;

@RequiredArgsConstructor
@RestController
public class AccountControllerImpl implements AccountController{
        
    private final AccountService accountService;   


	@Override
	public ResponseEntity<?> createClient(Client client) {
		return accountService.createClient(client);
	}

	@Override
	public ResponseEntity<?> login(AccountCredentialsDTO account) {
		return accountService.login(account);
	}

	@Override
	public HttpStatus createPlaceholders() {
		return accountService.createPlaceholders();
	}

}