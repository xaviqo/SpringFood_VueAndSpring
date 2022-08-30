package xavi.tech.springfood.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Address {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long addressId;
	@Column(nullable = false, length = 45)
	private String city;
	@Column(nullable = false, length = 100)
	private String address;
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonBackReference
	private Client client;
    @Column(nullable = false)
    private boolean main;
    
	public Address(String city, String address) {
		super();
		this.city = city;
		this.address = address;
	}
	
	public Address(long id) {
		this.addressId = id;
	}
    
    

}
