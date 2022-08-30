package xavi.tech.springfood.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.model.Client;
import xavi.tech.springfood.service.AccountService;

@AllArgsConstructor
@RestController
@PreAuthorize("permitAll()")
@RequestMapping(path="/api/account")
public class AccountController{
        
    private final AccountService accountService;

    @PostMapping(path="/createClient")
	public ResponseEntity<?> createClient(@RequestBody Client client) {
		return accountService.createClient(client);
	}
    
	@RequestMapping(path="/login")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody AccountCredentialsDTO account) {
		return accountService.login(account);
	}

}