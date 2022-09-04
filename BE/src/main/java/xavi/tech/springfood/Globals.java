package xavi.tech.springfood;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

public class Globals {
	
	//@Value("${cloudinary.apiname}")
	public static String CLOUD_API_NAME="xaviqo";
	//@Value("${cloudinary.apikey}")
	public static String CLOUD_API_KEY="454356868378879";
	//@Value("${cloudinary.apisec}")
	public static String CLOUD_API_SEC="Uik2ikc0KpU2hLpfy3esQRk_TY4";
	//@Value("${stripe.sec}")
	public static String STRIPE_API_SEC="sk_test_51LXY5zKpmz3t5YGvBrztUwZJUsMGLEWMJ0egDaOXgqpXT1AO7MX4hnctxP4mBqjRVzm2fooTFDY7qkH7Ji5Bnm8E00BZtIM0YS";
		
	public static Map<String, Boolean> getClientNavBar(){
		
		Map<String, Boolean> clientNav = new HashMap<>();
		
		clientNav.put("home", true);
		clientNav.put("menu", true);
		clientNav.put("cprofile", true);
		clientNav.put("login", false);
		clientNav.put("_orders_board", false);
		clientNav.put("_deliveries", false);
		clientNav.put("_team_manager", false);
		clientNav.put("_stock_manager", false);
		clientNav.put("_test_app", false);
		clientNav.put("logout", true);

		return clientNav;
		
	}
	
	public static Map<String, Boolean> getWorkerNavBar(){
		
		Map<String, Boolean> workerNav = new HashMap<>();
		
		workerNav.put("home", true);
		workerNav.put("menu", false);
		workerNav.put("cprofile", false);
		workerNav.put("login", false);
		workerNav.put("_deliveries", true);
		workerNav.put("logout", true);

		return workerNav;

	}
	
}
