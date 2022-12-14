package xavi.tech.springfood.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import xavi.tech.springfood.Globals;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn
public class Worker extends Account{
	
	private static final long serialVersionUID = 2906981394534301731L;
	
	private boolean deliverManager;
	private boolean orderManager;
	private boolean productManager;
	private boolean adminSfCfg;
	
	@Transient
	private boolean ready;

	public Worker(String name, String phone, String email, String password) {
		super(name, phone, email, password, Role.WORKER);
	}

	public Worker() {
		super(Role.WORKER);
	}
	
	@Override
	public Map<String, Boolean> getNavBar() {
		
		Map<String, Boolean> workerNav = new HashMap<>();
		
		workerNav.putAll(Globals.getWorkerNavBar());
		
		workerNav.put("_orders_board", orderManager);
		workerNav.put("_sf_cfg", adminSfCfg);
		workerNav.put("_deliver_manager", deliverManager);
		workerNav.put("_test_app", false);		
		
		return workerNav;
		
	}
	
	
}
