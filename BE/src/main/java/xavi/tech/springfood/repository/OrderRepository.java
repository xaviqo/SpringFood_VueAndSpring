package xavi.tech.springfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import xavi.tech.springfood.model.Order;
import xavi.tech.springfood.projection.dasboardOrdersProjection;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>{
	
	@Query(value = "SELECT ord.order_id AS orderId, "
			+ "ord.order_date as timestamp, "
			+ "cl.name as name, "
			+ "cl.phone as phone, "
			+ "ad.city as city, "
			+ "ad.address as address, "
			+ "ord.total_amount as totalAmount, "
			+ "ord.paid as paid, "
			+ "ord.delivered as delivered "
			+ "FROM order_main ord INNER JOIN account cl "
			+ "ON ord.client_id = cl.user_id INNER JOIN address ad "
			+ "ON ord.client_id = ad.client_id "
			+ "WHERE ad.main = true", nativeQuery = true)
	List<dasboardOrdersProjection> getAllOrders();
	
	@Query(value = "SELECT ord.order_id AS orderId, "
			+ "ord.order_date as timestamp, "
			+ "cl.name as name, "
			+ "cl.phone as phone, "
			+ "ad.city as city, "
			+ "ad.address as address, "
			+ "ord.total_amount as totalAmount, "
			+ "ord.paid as paid, "
			+ "ord.delivered as delivered "
			+ "FROM order_main ord INNER JOIN account cl "
			+ "ON ord.client_id = cl.user_id INNER JOIN address ad "
			+ "ON ord.client_id = ad.client_id "
			+ "WHERE ad.main = true "
			+ "AND ord.order_id LIKE %:prefixDateFilter", nativeQuery = true)
	List<dasboardOrdersProjection> getTodayOrders(@Param("prefixDateFilter") String prefixDateFilter);

}
