package xavi.tech.springfood;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.model.Address;
import xavi.tech.springfood.model.Client;
import xavi.tech.springfood.model.CreditCard;
import xavi.tech.springfood.model.Order;
import xavi.tech.springfood.model.OrderLine;
import xavi.tech.springfood.model.Product;
import xavi.tech.springfood.model.Worker;
import xavi.tech.springfood.repository.ClientRepository;
import xavi.tech.springfood.repository.OrderRepository;
import xavi.tech.springfood.repository.ProductRepository;
import xavi.tech.springfood.repository.WorkerRepository;

@AllArgsConstructor
@SpringBootApplication
public class SpringFoodApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringFoodApplication.class, args);
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private ProductRepository productRepository;
	private ClientRepository clientRepository;
	private WorkerRepository  workerRepository;
	private OrderRepository orderRepository;

	@Override
	public void run(String... args) throws Exception {
				
		Client cli1 = new Client();
		Client cli2 = new Client();
		Address add1 = new Address("Barelona", "Carrer Figuera, 48");
		Address add2 = new Address("Palau", "Av. Catalunya, 22");
		CreditCard cc1 = new CreditCard(1234567891234567L, 12, 22);
		CreditCard cc2 = new CreditCard(9876543219876543L, 04, 24);
		Worker wrk1 = new Worker();
		Worker wrk2 = new Worker();
		Product pro1 = new Product();
		Product pro2 = new Product();
		Product pro3 = new Product();
		Order order1 = new Order();
		Order order2 = new Order();
		OrderLine line11 = new OrderLine();
		OrderLine line12 = new OrderLine();
		OrderLine line21 = new OrderLine();


		// CLIENTS - WORKER - CC - ADD //

		add1.setClient(cli1);
		add2.setClient(cli2);
		cc1.setClient(cli1);
		cc2.setClient(cli2);

		cli1.setName("Paula");
		cli1.setPhone("606624414");
		cli1.setEmail("hola@caracola.com");
		cli1.setPassword("pass123");
		cli1.setPassword(passwordEncoder().encode(cli1.getPassword()));
		cli1.setAddress(add1);
		cli1.setCreditCards(cc1);
		add1.setMain(true);

		cli2.setName("Xavi");
		cli2.setPhone("634992303");
		cli2.setEmail("adios@dew.com");
		cli2.setPassword("pass123");
		cli2.setPassword(passwordEncoder().encode(cli2.getPassword()));
		cli2.setAddress(add2);
		cli2.setCreditCards(cc2);
		add2.setMain(true);

		clientRepository.save(cli1);
		clientRepository.save(cli2);

		wrk1.setTeamManager(false);
		wrk1.setOrderManager(true);
		wrk1.setProductManager(false);
		wrk1.setName("Raul");
		wrk1.setPhone("643923654");
		wrk1.setEmail("hey@hola.com");
		wrk1.setPassword("pass123");
		wrk1.setPassword(passwordEncoder().encode(wrk1.getPassword()));
		
		wrk2.setTeamManager(true);
		wrk2.setOrderManager(true);
		wrk2.setProductManager(true);
		wrk2.setName("Pepe");
		wrk2.setPhone("666555444");
		wrk2.setEmail("work@work.com");//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("http://localhost:1337"));
//		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
		wrk2.setPassword("pass123");
		wrk2.setPassword(passwordEncoder().encode(wrk2.getPassword()));

		workerRepository.save(wrk1);
		workerRepository.save(wrk2);

		// PRODUCTS //

		pro1.setName("Spring Pizza");
		pro1.setDescription("When the cheese comes out everybodys happy lancashire cheese triangles");
		pro1.setStock(14);
		pro1.setPrice(12.95);
		pro2.setName("Spring Burger");
		pro2.setDescription("Ecocinni squirty cheese manchego mozzarella fondue cottage cheese taleggio");
		pro2.setStock(6);
		pro2.setPrice(9);
		pro3.setName("Spring Soda");
		pro3.setDescription("Stinking bishop taleggio cheese and wine fondue cottage cheese taleggio comes out everybodys happy");
		pro3.setStock(30);
		pro3.setPrice(2.50);

		productRepository.save(pro1);
		productRepository.save(pro2);
		productRepository.save(pro3);

		// ORDERS //

		order1.setClient(cli1);
		order1.setWorker(wrk1);
		order1.setTimestamp(new Timestamp(System.currentTimeMillis()));
		line11.setProduct(pro3);
		line11.setQuantity(2);
		line11.setOrder(order1);
		line12.setProduct(pro1);
		line12.setQuantity(3);
		line12.setOrder(order1);
		order1.setOrderLine(line11);
		order1.setOrderLine(line12);
		order1.setPaid(true);
		order1.setDelivered(true);


		order2.setClient(cli2);
		order2.setWorker(wrk1);
		order2.setTimestamp(new Timestamp(System.currentTimeMillis()));
		line21.setProduct(pro1);
		line21.setQuantity(3);
		line21.setOrder(order2);
		order2.setOrderLine(line21);
		order2.setPaid(true);
		order2.setDelivered(false);

		orderRepository.save(order1);
		orderRepository.save(order2);
		
	}
}
