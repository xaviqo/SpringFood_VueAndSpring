package xavi.tech.springfood.repository;

import org.springframework.data.repository.CrudRepository;

import xavi.tech.springfood.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
