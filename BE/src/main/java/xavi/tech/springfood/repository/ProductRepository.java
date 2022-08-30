package xavi.tech.springfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import xavi.tech.springfood.model.Product;
import xavi.tech.springfood.projection.MenuProductProjection;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{

	@Query(value = "SELECT prd.product_id AS productId, "
			+ "prd.name AS name, "
			+ "prd.description AS description, "
			+ "prd.price AS price, "
			+ "prd.cloud_id AS cloudId, "
			+ "prd.type AS type "
			+ "FROM product prd "
			+ "WHERE prd.use_stock = FALSE "
			+ "OR prd.stock > 0", nativeQuery = true)
	List<MenuProductProjection> getMenuProducts();
	
	List<Product> findAll();
	
	@Query(value = "SELECT prd.price "
			+ "FROM product prd "
			+ "WHERE prd.product_id = :productId", nativeQuery = true)
	long findPriceByProductId(@Param("productId") long id);
	
	
}
