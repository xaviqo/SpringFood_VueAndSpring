package xavi.tech.springfood.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ord_seq")
    @GenericGenerator(
        name = "ord_seq", 
        strategy = "xavi.tech.springfood.utils.OrderIdGenerator", 
        parameters = {
            @Parameter(name = OrderIdGenerator.INCREMENT_PARAM, value = "50"),
            @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "SF_")})
	private String orderId;
    @Column
    private String stripeId;
    @ManyToOne
    @JoinColumn(name="client_id")
	private Client client;
    @ManyToOne
    @JoinColumn(name="responsible_id")
	private Worker worker;
	@Column(name="order_date", nullable = false)
	private LocalDateTime timestamp;
	@Column(nullable = false)
	private long totalAmount;
	@Column(nullable = false)
	private boolean paid;
	@Column(nullable = false)
	private boolean delivered;
	@OneToMany(mappedBy="order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OrderLine> orderLines;
	@ManyToOne
	@JoinColumn(name = "address_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Address deliveryAddress;
	@Column
	private String deliveryTime;
	
	public Order(LocalDateTime timestamp, long totalAmount, boolean paid, boolean delivered) {
		super();
		this.timestamp = timestamp;
		this.totalAmount = totalAmount;
		this.paid = paid;
		this.delivered = delivered;
		this.orderLines = new ArrayList<>();
		this.timestamp = LocalDateTime.now();
	}
	
	public Order() {
		super();
		this.orderLines = new ArrayList<OrderLine>();
		this.timestamp = LocalDateTime.now();
	}

	public void setOrderLine(OrderLine orderLine) {
		totalAmount += orderLine.getTotalLine();
		this.orderLines.add(orderLine);
	}
	
	public void setStripeId(String id) {
		if (Objects.nonNull(id)) {
			this.stripeId = id;
			this.paid = true;
		}
	}

}
