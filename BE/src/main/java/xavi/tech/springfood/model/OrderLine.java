package xavi.tech.springfood.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@ToString
@Entity
public class OrderLine {
    
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long lineId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="main_order")
    @JsonBackReference
	private Order order;
	@Column(nullable = false)
	private long quantity;
//	TODO: Maybe transient? this.getProduct().getPrice()*this.quantity;
	@Column(nullable = false)
	private long totalLine;
		
	public OrderLine(Product product, long quantity, long totalLine) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.totalLine = totalLine;
	}
	
	public OrderLine(){
		
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
		if (Objects.nonNull(this.product)) {
			this.totalLine = this.getProduct().getPrice()*this.quantity;
		}
	}

	
	
}
