package xavi.tech.springfood.model;

import java.sql.Timestamp;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xavi.tech.springfood.utils.IdGenerator;
import xavi.tech.springfood.utils.OrderIdGenerator;

@Setter
@Getter
@Entity
@ToString
@Table(name="order_main")
public class Order {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_seq")
    @GenericGenerator(
        name = "acc_seq", 
        strategy = "xavi.tech.springfood.utils.OrderIdGenerator", 
        parameters = {
            @Parameter(name = OrderIdGenerator.INCREMENT_PARAM, value = "50"),
            @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "SF_")})
	private String orderId;
    
    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
	private Client client;
    @ManyToOne
    @JoinColumn(name="responsible_id", nullable=false)
	private Worker worker;
	@Column(name="order_date", nullable = false)
	private Timestamp timestamp;
	@Column(nullable = false)
	private double totalAmount;
	@Column(nullable = false)
	private boolean paid;
	@Column(nullable = false)
	private boolean delivered;
	@OneToMany(mappedBy="order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OrderLine> orderLines;
	
	public Order(Timestamp timestamp, double totalAmount, boolean paid, boolean delivered) {
		super();
		this.timestamp = timestamp;
		this.totalAmount = totalAmount;
		this.paid = paid;
		this.delivered = delivered;
		this.orderLines = new ArrayList<>();
	}
	
	public Order() {
		super();
		this.orderLines = new ArrayList<OrderLine>();
	}

	public void setOrderLine(OrderLine orderLine) {
		this.orderLines.add(orderLine);
	}

}
