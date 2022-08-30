package xavi.tech.springfood;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.model.Address;
import xavi.tech.springfood.model.Client;
import xavi.tech.springfood.model.Order;
import xavi.tech.springfood.model.OrderLine;
import xavi.tech.springfood.model.Product;
import xavi.tech.springfood.model.SpringFoodCfg;
import xavi.tech.springfood.model.Worker;
import xavi.tech.springfood.repository.ClientRepository;
import xavi.tech.springfood.repository.OrderRepository;
import xavi.tech.springfood.repository.ProductRepository;
import xavi.tech.springfood.repository.SfCfgRepository;
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
	private SfCfgRepository cfgRepository;

	@Override
	public void run(String... args) throws Exception {
				
		Client cli1 = new Client();
		Client cli2 = new Client();
		Address add1 = new Address("Barelona", "Carrer Figuera, 48");
		Address add12 = new Address("Girona", "Plç. Catalunya, 123");
		Address add2 = new Address("Palau", "Av. Catalunya, 22");
		Worker wrk1 = new Worker();
		Worker wrk2 = new Worker();
		
		SpringFoodCfg cfg = new SpringFoodCfg();
		
		cfg.setFirstDeliveryAvailable(LocalTime.of(20, 00));
		cfg.setLastDeliveryAvailable(LocalTime.of(23, 00));
		cfg.setOpenDashboardTime(LocalTime.of(19, 00));
		cfg.setCloseDashboardTime(LocalTime.of(01, 00));
		cfg.setDashboardTableDaysDifference(1);
		cfg.setMinutesTimeSlot(30);
		
		cfgRepository.save(cfg);
		
		cfg.generateDeliveryTimes();
		
		Product pro1 = new Product();
		Product pro2 = new Product();
		Product pro3 = new Product();
		Product pro4 = new Product();
		Product pro5 = new Product();
		Product pro6 = new Product();
		Product pro7 = new Product();
		Product pro8 = new Product();
		
		
		Order order1 = new Order();
		Order order2 = new Order();
		Order order3 = new Order();
		OrderLine line11 = new OrderLine();
		OrderLine line12 = new OrderLine();
		OrderLine line21 = new OrderLine();
		OrderLine line31 = new OrderLine();


		// CLIENTS - WORKER - CC - ADD //

		add1.setClient(cli1);
		add12.setClient(cli1);
		add2.setClient(cli2);

		cli1.setName("Paula");
		cli1.setPhone("606624414");
		cli1.setEmail("hola@caracola.com");
		cli1.setPassword("pass123");
		cli1.setPassword(passwordEncoder().encode(cli1.getPassword()));
		add1.setMain(true);
		cli1.setAddress(add1);
		add12.setMain(false);
		cli1.setAddress(add12);

		cli2.setName("Xavi");
		cli2.setPhone("634992303");
		cli2.setEmail("adios@dew.com");
		cli2.setPassword("pass123");
		cli2.setPassword(passwordEncoder().encode(cli2.getPassword()));
		cli2.setAddress(add2);
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
		wrk2.setEmail("work@work.com");
		wrk2.setPassword("pass123");
		wrk2.setPassword(passwordEncoder().encode(wrk2.getPassword()));

		workerRepository.save(wrk1);
		workerRepository.save(wrk2);

		// PRODUCTS //

		pro1.setName("Spring Burger");
		pro1.setDescription("When the cheese comes out everybodys happy lancashire cheese triangles");
		pro1.setStock(14);
		pro1.setPrice(1295);
		pro1.setUseStock(false);
		pro1.setCloudId("risiyveoolfkddy2b3vo");
		pro1.setType("burger");
		
		pro2.setName("Spring Pizza");
		pro2.setDescription("Ecocinni squirty cheese manchego mozzarella fondue cottage cheese taleggio");
		pro2.setStock(6);
		pro2.setPrice(900);
		pro2.setUseStock(true);
		pro2.setCloudId("bwdixrmxlfztzbjpgukc");
		pro2.setType("pizza");
		
		pro3.setName("Spring Soda");
		pro3.setDescription("Stinking bishop taleggio cheese and wine fondue cottage cheese taleggio comes out everybodys happy");
		pro3.setStock(30);
		pro3.setPrice(150);
		pro3.setUseStock(true);
		pro3.setCloudId("oabhdwhguexgt01mwmza");
		pro3.setType("drink");
		
		pro4.setName("Spring Salad");
		pro4.setDescription("Jarlsberg red leicester cheese triangles. Pepper jack monterey jack cauliflower cheese  cheese strings");
		pro4.setStock(0);
		pro4.setPrice(2125);
		pro4.setUseStock(false);
		pro4.setCloudId("gotm6box4zef6ku80d9y");
		pro4.setType("salad");
		
		pro5.setName("Spring Not Using Stock");
		pro5.setDescription("I don't care about stock!");
		pro5.setStock(0);
		pro5.setPrice(125);
		pro5.setUseStock(true);
		pro5.setCloudId("k8ntwlbsroxnpkyazna0");
		pro5.setType("burger");
		
		pro6.setName("Venga Vegan Pizza");
		pro6.setDescription("Leicester cheese triangles. Pepper jack monterey jack cauliflower cheese macaroni cheese fondue cheddar");
		pro6.setStock(11);
		pro6.setPrice(1337);
		pro6.setUseStock(true);
		pro6.setCloudId("rwu47rmn1keq7q98o0e8");
		pro6.setType("pizza");
		
		pro7.setName("Pasta Al-Pastorinni");
		pro7.setDescription("Manchego mozzarella fondue cottage taleggio macaroni cheese fondue cheddar danish fontina tagliattello");
		pro7.setStock(9);
		pro7.setPrice(1100);
		pro7.setUseStock(true);
		pro7.setCloudId("jait18jwpkhb2clbsnrw");
		pro7.setType("pasta");
		
		pro8.setName("La Green Burger");
		pro8.setDescription("Plant based burger is for everyone, it's been decades in the making!");
		pro8.setStock(0);
		pro8.setPrice(1195);
		pro8.setUseStock(false);
		pro8.setCloudId("zt06xvmfijvqnpylhf4x");
		pro8.setType("burger");
		
		productRepository.save(pro1);
		productRepository.save(pro2);
		productRepository.save(pro3);
		productRepository.save(pro4);
		productRepository.save(pro5);
		productRepository.save(pro6);
		productRepository.save(pro7);
		productRepository.save(pro8);

		// ORDERS //

		order1.setClient(cli1);
		order1.setWorker(wrk1);
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
		order1.setDeliveryAddress(add1);
		order1.setDeliveryTime("20:30");


		order2.setClient(cli2);
		order2.setWorker(wrk1);
		order2.setTimestamp(LocalDateTime.now().plusDays(1).plusHours(12));
		line21.setProduct(pro1);
		line21.setQuantity(3);
		line21.setOrder(order2);
		order2.setOrderLine(line21);
		order2.setPaid(true);
		order2.setDelivered(false);
		order2.setDeliveryAddress(add12);
		order2.setDeliveryTime("21:30");
		
		order3.setClient(cli2);
		order3.setWorker(wrk1);
		order3.setTimestamp(LocalDateTime.now().plusHours(11));
		line31.setProduct(pro8);
		line31.setQuantity(2);
		line31.setOrder(order3);
		order3.setOrderLine(line31);
		order3.setPaid(true);
		order3.setDelivered(true);
		order3.setDeliveryAddress(add2);
		order3.setDeliveryTime("19:00");

		orderRepository.save(order1);
		orderRepository.save(order2);
		orderRepository.save(order3);

		
	}
}
