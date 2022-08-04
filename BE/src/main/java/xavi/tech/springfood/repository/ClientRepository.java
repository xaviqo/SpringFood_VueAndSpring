package xavi.tech.springfood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import xavi.tech.springfood.model.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long>{
	
	<Optional>Client findByEmail(String email);

}
