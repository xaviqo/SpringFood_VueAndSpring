package xavi.tech.springfood.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	@Column(nullable = false, length = 60)
	private String name;
	@Column(length= 150)
	private String description;
	@Column(nullable = false)
	private long stock;
	@Column(nullable = false)
	private long price;
	@Column(length = 20)
	private String cloudId;
	@Column(length = 15)
	private String type;
	@Column 
	private boolean useStock;
	
	
	public Product(long productId, long price) {
		super();
		this.productId = productId;
		this.price = price;
	}
	
	
	
}
