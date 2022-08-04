package xavi.tech.springfood.service;

import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import xavi.tech.springfood.dto.AccountCredentialsDTO;
import xavi.tech.springfood.mapper.ClientMapper;
import xavi.tech.springfood.mapper.WorkerMapper;
import xavi.tech.springfood.model.Client;
import xavi.tech.springfood.model.Role;
import xavi.tech.springfood.model.Worker;
import xavi.tech.springfood.repository.ClientRepository;
import xavi.tech.springfood.repository.WorkerRepository;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService{

	private final ClientRepository clientRepository;
	private final WorkerRepository workerRepository;
	
	@Override
	public ResponseEntity<Client> createClient(Client client) {
				
		try {
			return new ResponseEntity<Client>(clientRepository.save(client),HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	
		return null;
	}

	@Override
	public ResponseEntity<Worker> createWorker(Worker worker) {
		
		try {
			return new ResponseEntity<Worker>(workerRepository.save(worker),HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.toString());
		}		
		
		return null;
	}

	@Override
	public ResponseEntity<?> getAccount(AccountCredentialsDTO account) {
		
		if (account.getRole().equals(Role.CLIENT)) {
			return new ResponseEntity<Client>(clientRepository.findByEmail(account.getEmail()),HttpStatus.OK);
		} else if (account.getRole().equals(Role.WORKER)){
			return new ResponseEntity<Worker>(workerRepository.findByEmail(account.getEmail()),HttpStatus.OK);
		}
		
		return null;
	}
	
	
	
	
}
