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
	
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "client_id")
	private Client client;

	public CreditCard(long n, int m, int y) {
		super();
		this.number = n;
		this.month = m;
		this.year = y;
	}
    
    

}
