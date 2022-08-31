package xavi.tech.springfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import xavi.tech.springfood.model.Address;
import xavi.tech.springfood.projection.DeliveryDataProjection;

@Transactional
public interface AddressRepository extends CrudRepository<Address, Long>{
	
	int deleteByAddressId(long id);
	
    @Modifying
	@Query(value = "UPDATE address "
			+ "SET main = true "
			+ "WHERE address_id = :id", nativeQuery = true)
	int updateToMain(@Param("id") long id);
    
    @Modifying
	@Query(value = "UPDATE address "
			+ "SET main = false "
			+ "WHERE main = true AND client_id = :clientId", nativeQuery = true)
    int updateMainAddressStatus(@Param("clientId") String clientId);
	
	
	@Query(value = "SELECT adr.main FROM address adr "
			+ "WHERE adr.address_id = :id", nativeQuery = true)
	Boolean isAddressMain(long id);
	
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
