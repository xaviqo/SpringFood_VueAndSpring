package xavi.tech.springfood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import xavi.tech.springfood.model.Worker;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, Long>{
	
	<Optional>Worker findByEmail(String email);


}
