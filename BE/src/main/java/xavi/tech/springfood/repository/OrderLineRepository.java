package xavi.tech.springfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xavi.tech.springfood.model.OrderLine;
import xavi.tech.springfood.projection.DashboardOrderLinesProjection;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long>{

	List<DashboardOrderLinesProjection> findByOrderOrderId(String orderId);
}
