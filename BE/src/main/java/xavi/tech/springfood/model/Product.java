package xavi.tech.springfood.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Product {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
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
