package xavi.tech.springfood.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class CreditCard {
	
	@Id @Column(nullable = false, length = 16)
	private long number;
	@Column(nullable = false, length = 2)
	private byte month;
	@Column(nullable = false, length = 2)
	private byte year;
	@Column(nullable = false, length = 3)
	private byte secretCode;
	
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "client_id")
	private Client client;

	public CreditCard(int number, byte month, byte year, byte secretCode) {
		super();
		this.number = number;
		this.month = month;
		this.year = year;
		this.secretCode = secretCode;
	}
    
    

}
