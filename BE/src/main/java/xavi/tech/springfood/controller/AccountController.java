package xavi.tech.springfood.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.model.Client;
import xavi.tech.springfood.model.Worker;

@RestController
@RequestMapping(path="/api/account")
public interface AccountController {

	@RequestMapping(path="/client")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Client> createClientAccount(@RequestBody Client client); 
	
	@RequestMapping(path="/worker")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Worker> createWorkerAccount(@RequestBody Worker worker);
	
	@RequestMapping(path="/get")
	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getAccount(@RequestBody AccountCredentialsDTO account); 
}
