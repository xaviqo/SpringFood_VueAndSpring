package xavi.tech.springfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import xavi.tech.springfood.model.Order;
import xavi.tech.springfood.model.OrderLine;
import xavi.tech.springfood.projection.DashboardOrderLinesProjection;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long>{
	
	List<OrderLine> findByOrder(Order order);
	
	@Query(value = "SELECT ol.line_id AS lineId, "
			+ "pr.product_id AS productId, "
			+ "pr.name AS productName, "
			+ "ol.quantity AS quantity, "
			+ "ol.total_line as totalLine "
			+ "FROM order_line ol "
			+ "JOIN product pr "
			+ "ON ol.product_id = pr.product_id "
			+ "WHERE ol.main_order = :mainId", nativeQuery = true)
	List<DashboardOrderLinesProjection> orderLinesDashboard(@Param("mainId") String mainOrderId);

}
