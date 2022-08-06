package xavi.tech.springfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.model.Client;
import xavi.tech.springfood.model.Worker;
import xavi.tech.springfood.service.AccountService;

@RestController
public class AccountControllerImpl implements AccountController{
	
        
    @Autowired
    private AccountService accountService;


	@Override
	public ResponseEntity<Client> createClientAccount(Client client) {
		return accountService.createClient(client); 
	}

	@Override
	public ResponseEntity<Worker> createWorkerAccount(Worker worker) {
		return accountService.createWorker(worker);
	}

	@Override
	public ResponseEntity<?> getAccount(AccountCredentialsDTO account) {
		return accountService.getAccount(account);
	}

	@Override
	public HttpStatus createClients() {
		return accountService.createClients();
	}
	
	

}