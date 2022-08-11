package xavi.tech.springfood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import xavi.tech.springfood.model.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, String>{
	
	<Optional>Client findByEmail(String email);

}
