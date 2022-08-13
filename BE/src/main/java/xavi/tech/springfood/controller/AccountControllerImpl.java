package xavi.tech.springfood.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.model.Client;
import xavi.tech.springfood.service.AccountService;
import xavi.tech.springfood.service.OrderService;

@RequiredArgsConstructor
@RestController
public class AccountControllerImpl implements AccountController{
        
    private final AccountService accountService;
    private final OrderService test;

	@Override
	public ResponseEntity<?> createClient(Client client) {
		return accountService.createClient(client);
	}

	@Override
	public ResponseEntity<?> login(AccountCredentialsDTO account) {
		return accountService.login(account);
	}
	
	@Override
	public ResponseEntity<?> orderTest() {
		return test.getAllOrders();
	}

}