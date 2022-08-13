package xavi.tech.springfood;

import java.util.HashMap;
import java.util.Map;

public class Globals {
		
	public static Map<String, Boolean> getClientNavBar(){
		
		Map<String, Boolean> clientNav = new HashMap<>();
		
		clientNav.put("home", true);
		clientNav.put("login", false);
		clientNav.put("_orders_board", false);
		clientNav.put("_deliveries", false);
		clientNav.put("_team_manager", false);
		clientNav.put("_product_manager", false);
		clientNav.put("_test_app", false);

		return clientNav;
		
	}
	
	public static Map<String, Boolean> getWorkerNavBar(){
		
		Map<String, Boolean> workerNav = new HashMap<>();
		
		workerNav.put("home", true);
		workerNav.put("login", false);
		workerNav.put("_deliveries", true);

		return workerNav;

	}
	
}
