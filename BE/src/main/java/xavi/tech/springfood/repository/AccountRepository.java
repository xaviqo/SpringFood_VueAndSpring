package xavi.tech.springfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import xavi.tech.springfood.model.Account;
import xavi.tech.springfood.model.Role;

public interface AccountRepository extends JpaRepository<Account, String>{
	
	Account findByEmail(String email);
	boolean existsByEmail(String email);
	@Query(value = "SELECT ac.role FROM account ac WHERE ac.email = :loginEmail", nativeQuery = true)
	Role getRoleByEmail(@Param("loginEmail") String loginEmail);
	
}
