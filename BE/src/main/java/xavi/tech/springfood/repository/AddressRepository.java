package xavi.tech.springfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import xavi.tech.springfood.model.Address;
import xavi.tech.springfood.projection.DeliveryDataProjection;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
	@Query(value = "SELECT adr.address AS address, "
			+ "adr.address_id AS id, "
			+ "adr.city AS city, "
			+ "adr.main AS main,"
			+ "aco.phone AS phone "
			+ "FROM address adr "
			+ "JOIN account aco "
			+ "ON adr.client_id = aco.user_id "
			+ "WHERE aco.email = :email", nativeQuery = true)
	List<DeliveryDataProjection> getDeliveryDataByEmail(@Param("email") String clientId);

}
