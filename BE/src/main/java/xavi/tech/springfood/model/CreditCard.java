package xavi.tech.springfood.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private int month;
	@Column(nullable = false, length = 2)
	private int year;
	@Column(nullable = false, length = 3)
	private int secretCode;
	
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "client_id")
	private Client client;

	public CreditCard(long number, int i, int j, int k) {
		super();
		this.number = number;
		this.month = i;
		this.year = j;
		this.secretCode = k;
	}
    
    

}
