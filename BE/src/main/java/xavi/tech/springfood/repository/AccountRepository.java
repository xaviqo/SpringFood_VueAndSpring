package xavi.tech.springfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xavi.tech.springfood.model.Account;
import xavi.tech.springfood.model.Role;

public interface AccountRepository extends JpaRepository<Account, String>{
	
	<Optional>Account findByEmail(String email);
	boolean existsByEmail(String email);
	
}
