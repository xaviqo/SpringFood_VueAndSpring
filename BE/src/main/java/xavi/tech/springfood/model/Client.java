package xavi.tech.springfood.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn
@ToString(callSuper=true, includeFieldNames=true)
public class Client extends Account {
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.PERSIST)
    private List<CreditCard> creditCards;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.PERSIST)
    private List<Address> addresses;
	
	public Client(String name, String phone, String email, String password) {
		super(name, phone, email, password, Role.CLIENT);
		addresses = new ArrayList<Address>();
	}

	public Client() {
		super(Role.CLIENT);
	}

	public void setAddress(Address address) {
		this.addresses.add(address);
	}

	public Client(String name, String phone, String email, String password, Role role) {
		super(name, phone, email, password, role);
	}

	
    
}
