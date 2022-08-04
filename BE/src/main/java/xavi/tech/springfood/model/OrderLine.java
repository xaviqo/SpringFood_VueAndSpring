package xavi.tech.springfood.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
public class OrderLine {
    
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long lineId;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	@Column(nullable = false)
	private long quantity;
	@Column(nullable = false)
	private double amount;
	
	public OrderLine(Product product, long quantity, double amount) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.amount = amount;
	}
	
	
}
