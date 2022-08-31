package xavi.tech.springfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import xavi.tech.springfood.model.Account;
import xavi.tech.springfood.model.Role;

@Transactional
@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
	
	Account findByEmail(String email);
	boolean existsByEmail(String email);
	@Query(value = "SELECT ac.role FROM account ac WHERE ac.email = :loginEmail", nativeQuery = true)
	Role getRoleByEmail(@Param("loginEmail") String loginEmail);
	
    @Modifying
	@Query(value = "UPDATE account "
			+ "SET name = :accountName, "
			+ "email = :accountEmail, "
			+ "phone = :accountPhone "
			+ "WHERE user_id = :accountId", nativeQuery = true)
    int updateAccountInfo(@Param("accountId") String accountId,
			    		@Param("accountName") String accountName, 
			    		@Param("accountEmail") String accountEmail, 
			    		@Param("accountPhone") String accountPhone);
	
}
