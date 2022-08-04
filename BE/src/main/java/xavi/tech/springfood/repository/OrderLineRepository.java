package xavi.tech.springfood.repository;

import org.springframework.data.repository.CrudRepository;

import xavi.tech.springfood.model.OrderLine;

public interface OrderLineRepository extends CrudRepository<OrderLine, Long>{

}
