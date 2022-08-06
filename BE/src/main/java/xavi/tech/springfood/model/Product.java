package xavi.tech.springfood.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;
import xavi.tech.springfood.utils.IdGenerator;

@Getter
@Setter
@Entity
@Table
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_seq")
    @GenericGenerator(
        name = "acc_seq", 
        strategy = "xavi.tech.springfood.utils.IdGenerator", 
        parameters = {
            @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "50"),
            @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "PRO_")})
	private String productId;
	@Column(nullable = false, length = 60)
	private String name;
	@Column
	private String description;
	@Column(nullable = false)
	private long stock;
	@Column(nullable = false)
	private double price;
	@Column
	private String imgUri;
	
	public Product(String name, String description, long stock, double price, String imgUri) {
		super();
		this.name = name;
		this.description = description;
		this.stock = stock;
		this.price = price;
		this.imgUri = imgUri;
	}

	public Product() {
		super();
	}

	
	
	
}
