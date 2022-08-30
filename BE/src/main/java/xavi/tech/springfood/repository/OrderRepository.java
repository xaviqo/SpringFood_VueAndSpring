package xavi.tech.springfood.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import xavi.tech.springfood.model.Client;
import xavi.tech.springfood.model.Order;
import xavi.tech.springfood.projection.DashboardOrdersProjection;
import xavi.tech.springfood.projection.LastOrderCardProjection;
import xavi.tech.springfood.projection.ProductDemandCardProjection;

@Transactional
@Repository
public interface OrderRepository extends JpaRepository<Order, String>{
	
	List<Order> findByClient(Client client);
	
	@Query(value = "SELECT SUM(ol.total_line) AS sumTotal, "
			+ "COUNT(ol.product_id) AS countId, "
			+ "p.name AS name, "
			+ "p.stock AS stock, "
			+ "p.cloud_id AS cloud "
			+ "FROM order_line ol "
			+ "JOIN product p "
			+ "ON ol.product_id = p.product_id "
			+ "GROUP BY ol.product_id, p.name, p.stock, p.cloud_id "
			+ "ORDER BY COUNT(ol.product_id) ASC "
			+ "LIMIT 1", nativeQuery = true)
	ProductDemandCardProjection highProductDemand();
	
	@Query(value = "SELECT SUM(ol.total_line) AS sumTotal, "
			+ "COUNT(ol.product_id) AS countId, "
			+ "p.name AS name, "
			+ "p.stock AS stock, "
			+ "p.cloud_id AS cloud "
			+ "FROM order_line ol "
			+ "JOIN product p "
			+ "ON ol.product_id = p.product_id "
			+ "GROUP BY ol.product_id, p.name, p.stock, p.cloud_id "
			+ "ORDER BY COUNT(ol.product_id) DESC "
			+ "LIMIT 1", nativeQuery = true)
	ProductDemandCardProjection lowProductDemand();
	
	@Query(value = "SELECT om.total_amount "
			+ "FROM order_main om "
			+ "WHERE om.order_date >= :from "
			+ "AND om.order_date < :to "
			+ "AND om.paid = true", nativeQuery = true)
	List<Long> totalAmountsOfDay(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
	
	@Query(value = "SELECT cli.name AS client, "
			+ "cli.phone AS clientPhone, "
			+ "wrk.name AS responsible, "
			+ "om.order_date AS orderDate "
			+ "FROM order_main om "
			+ "LEFT JOIN account cli "
			+ "ON om.client_id = cli.user_id "
			+ "LEFT JOIN account wrk "
			+ "ON om.responsible_id = wrk.user_id "
			+ "ORDER BY om.order_date "
			+ "DESC LIMIT 1", nativeQuery = true)
	LastOrderCardProjection getLastOrder();
	
	@Query(value = "SELECT cli.name AS client, "
			+ "cli.phone AS clientPhone, "
			+ "wrk.name AS responsible, "
			+ "om.order_date AS orderDate "
			+ "FROM order_main om "
			+ "LEFT JOIN account cli "
			+ "ON om.client_id = cli.user_id "
			+ "LEFT JOIN account wrk "
			+ "ON om.responsible_id = wrk.user_id "
			+ "WHERE om.delivered = true "
			+ "ORDER BY om.order_date "
			+ "DESC LIMIT 1", nativeQuery = true)
	LastOrderCardProjection getLastDeliveredOrder();

	
	@Query(value = "SELECT COUNT(1) "
			+ "FROM order_main om "
			+ "WHERE om.order_date >= :dateLimit "
			+ "AND om.delivered = :delivered "
			+ "AND om.paid = true", nativeQuery = true)
	long deliveredPercentCard(@Param("dateLimit") LocalDate limit, @Param("delivered") boolean delivered);
	
    @Modifying
	@Query(value = "UPDATE order_main om "
			+ "SET paid = true "
			+ "WHERE om.order_id = :orderId", nativeQuery = true)
	void updateToPaid(@Param("orderId") String orderId);
		
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
			+ "ON ord.client_id = cl.user_id "
			+ "INNER JOIN address ad "
			+ "ON ord.address_id = ad.address_id", nativeQuery = true)
	List<DashboardOrdersProjection> getAllOrders();
	
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
			+ "ON ord.client_id = cl.user_id "
			+ "INNER JOIN address ad "
			+ "ON ord.address_id = ad.address_id "
			+ "WHERE ord.order_date >= :open "
			+ "AND ord.order_date <= :close", nativeQuery = true)
	List<DashboardOrdersProjection> getTodayOrders(@Param("open") LocalDateTime open, @Param("close") LocalDateTime close);

}
