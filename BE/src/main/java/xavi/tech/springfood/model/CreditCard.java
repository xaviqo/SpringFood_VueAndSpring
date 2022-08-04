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
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long cardId;
	@Column(nullable = false, length = 16)
	private int number;
	@Column(nullable = false, length = 2)
	private int month;
	@Column(nullable = false, length = 2)
	private int year;
	@Column(nullable = false, length = 3)
	private String secretCode;
	
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "client_id")
	private Client client;

	public CreditCard(int number, int month, int year, String secretCode) {
		super();
		this.number = number;
		this.month = month;
		this.year = year;
		this.secretCode = secretCode;
	}
    
    

}
