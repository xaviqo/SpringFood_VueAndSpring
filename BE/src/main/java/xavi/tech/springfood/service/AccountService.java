package xavi.tech.springfood.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.model.Client;


public interface AccountService {

	ResponseEntity<?> createClient(Client client);
	ResponseEntity<Map<String,Object>> login(AccountCredentialsDTO account);
}
