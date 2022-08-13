package xavi.tech.springfood.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn
@ToString(callSuper=true, includeFieldNames=true)
public class Client extends Account {
	
	private static final long serialVersionUID = -9058394394958156119L;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.PERSIST)
    private List<CreditCard> creditCards;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.PERSIST)
    private List<Address> addresses;
	
	public Client(String name, String phone, String email, String password) {
		super(name, phone, email, password, Role.CLIENT);
		addresses = new ArrayList<Address>();
		creditCards = new ArrayList<CreditCard>();
	}

//	public Client(String name, String phone, String email, String password, Role role) {
//		super(name, phone, email, password, role);
//	}
	
	public Client() {
		super(Role.CLIENT);
		addresses = new ArrayList<Address>();
		creditCards = new ArrayList<CreditCard>();
	}
	
	@Override
	public Map<String, Boolean> getNavBar() {
		return super.getNavBar();
	}

	public void setAddress(Address address) {
		this.addresses.add(address);
	}
	
	public void setCreditCards(CreditCard cc) {
		this.creditCards.add(cc);
	}




    
}
