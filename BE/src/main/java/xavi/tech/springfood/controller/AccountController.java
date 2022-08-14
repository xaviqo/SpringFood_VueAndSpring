package xavi.tech.springfood.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.model.Client;

//@CrossOrigin(origins = "http://localhost:1337", maxAge = 3600)
@RestController
@RequestMapping(path="/api/account")
public interface AccountController {
	
	@RequestMapping(path="/createClient")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> createClient(@RequestBody Client client); 
	
	@RequestMapping(path="/login")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> login(@RequestBody AccountCredentialsDTO account);
	
	@RequestMapping(path="/orderTest")
	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> orderTest();
}
