package xavi.tech.springfood.service;

import org.springframework.http.ResponseEntity;

import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.model.Client;
import xavi.tech.springfood.model.Worker;

public interface AccountService {

	ResponseEntity<Client> createClient(Client client);
	
	ResponseEntity<Worker> createWorker(Worker worker);

	ResponseEntity<?> getAccount(AccountCredentialsDTO account);
}
