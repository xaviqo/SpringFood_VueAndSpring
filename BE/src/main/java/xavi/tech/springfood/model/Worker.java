package xavi.tech.springfood.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn
public class Worker extends Account{
	
	private boolean accountManager;
	private boolean orderManager;
	private boolean productManager;

	public Worker(String name, String phone, String email, String password) {
		super(name, phone, email, password, Role.WORKER);
	}

	public Worker() {
		super(Role.WORKER);
	}
	
	
}
