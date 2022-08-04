package xavi.tech.springfood.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.ToString;

@Getter
@Entity
@ToString
@Table(name="order_main")
public class Order {
	
	//TODO: add client too
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;
	@Column(name="order_date", nullable = false)
	private LocalDateTime dateTime;
	@Column(nullable = false)
	private double totalAmount;
	@Column(nullable = false)
	private boolean paid;
	@Column(nullable = false)
	private boolean delivered;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name="order_id", nullable = false)
	@JsonManagedReference
	private List<OrderLine> orderLines;
	
	public Order(LocalDateTime dateTime, double totalAmount, boolean paid, boolean delivered) {
		super();
		this.dateTime = dateTime;
		this.totalAmount = totalAmount;
		this.paid = paid;
		this.delivered = delivered;
		this.orderLines = new ArrayList<>();
	}

	public void setOrderLine(OrderLine orderLine) {
		this.orderLines.add(orderLine);
	}
	
	
	
	
}
