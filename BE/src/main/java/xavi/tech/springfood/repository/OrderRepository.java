package xavi.tech.springfood.repository;

import org.springframework.data.repository.CrudRepository;

import xavi.tech.springfood.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
