package xavi.tech.springfood.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.dto.AccountEditDTO;
import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;
import xavi.tech.springfood.model.Account;
import xavi.tech.springfood.model.Client;
import xavi.tech.springfood.model.Role;
import xavi.tech.springfood.model.Worker;
import xavi.tech.springfood.repository.AccountRepository;
import xavi.tech.springfood.repository.ClientRepository;
import xavi.tech.springfood.security.jwt.JwtUtils;

@AllArgsConstructor
@Service
public class AccountService{

	private final AuthenticationManager authenticationManager;
	private final BCryptPasswordEncoder encoder;
	private final JwtUtils jwtUtil;
	private final AccountRepository accountRepository;
	private final ClientRepository clientRepository;
	
	/*
	 * SIGNOUT EXAMPLE HERE @ 02:20
	 * https://www.youtube.com/watch?v=ZBeyy4Q3nIw
	 */
	
	public ResponseEntity<Map<String,Object>> login(AccountCredentialsDTO accountDto){
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(accountDto.getEmail(),accountDto.getPassword());
		
		Authentication login = authenticationManager.authenticate(token);
				
		if (login.isAuthenticated()) {
			
			Account account = accountRepository.findByEmail(accountDto.getEmail());
			String loginToken = jwtUtil.generate(accountDto.getEmail());
			Map<String,Object> res = new HashMap<>();
			
			res.put("token", loginToken);
			res.put("email", login.getPrincipal().toString());
			res.put("name", account.getName());
			res.put("role",account.getRole().getDescription());
			res.put("moment", LocalDateTime.now().toString());
			
			// NAV BAR CONSTRUCTION - If its a worker, nav_bar is not Account.class default
			if (Role.WORKER.equals(account.getRole())) {
				account = (Worker) account;
			}
			
			res.put("nav_bar", account.getNavBar());
			
			return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK);
			
		}
		
		throw new SpringFoodException(SpringFoodError.InvalidJWToken,HttpStatus.UNAUTHORIZED);

	}

	public ResponseEntity<?> getId(){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Account account = accountRepository.findByEmail(authentication.getPrincipal().toString());
		
		try {
			return new ResponseEntity<String>(account.getUserId(),HttpStatus.OK);		
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.EmailRetrievingSfId,HttpStatus.UNAUTHORIZED);
		}


	}
	
	public ResponseEntity<?> editAccount(AccountEditDTO newAccountData){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Account account = accountRepository.findByEmail(authentication.getPrincipal().toString());
		
		if (account.getEmail().equals(newAccountData.getEmail()) || !accountRepository.existsByEmail(newAccountData.getEmail())) {
			return new ResponseEntity<Integer>(accountRepository.updateAccountInfo(
					account.getUserId(),
					newAccountData.getName(),
					newAccountData.getEmail(),				
					newAccountData.getPhone())
					,HttpStatus.OK);
		} else {
			throw new SpringFoodException(SpringFoodError.EmailAlreadyExists,HttpStatus.UNAUTHORIZED);
		}

	}

	public ResponseEntity<?> createClient(Client client){


		if (!accountRepository.existsByEmail(client.getEmail())) {

			try {
				
				client.setPassword(encoder.encode(client.getPassword()));
				clientRepository.save(client);
				
				return new ResponseEntity<Client>(HttpStatus.CREATED);
			} catch (Exception e) {
				throw new SpringFoodException(SpringFoodError.ErrorCreatingClient,HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}

		throw new SpringFoodException(SpringFoodError.EmailAlreadyExists,HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
}
