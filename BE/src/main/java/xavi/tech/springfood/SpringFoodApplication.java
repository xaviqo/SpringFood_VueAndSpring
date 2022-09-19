package xavi.tech.springfood;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;

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
		
		int clientsQuantity = 280;
		int workersQuantity = 40;
		int productsQuantity = 80;
		int ordersQuantity = 365;
		int maxLinesPerOrder = 8;
		int totalProducts = productsQuantity+8;
		
		String defaultPassword = "pass123";

		// CFG
		
		SpringFoodCfg cfg = new SpringFoodCfg();
		
		cfg.setFirstDeliveryAvailable(LocalTime.of(20, 00));
		cfg.setLastDeliveryAvailable(LocalTime.of(23, 00));
		cfg.setOpenDashboardTime(LocalTime.of(19, 00));
		cfg.setCloseDashboardTime(LocalTime.of(01, 00));
		cfg.setDashboardTableDaysDifference(1);
		cfg.setMinutesTimeSlot(30);
		
		cfg.generateDeliveryTimes();

		cfgRepository.save(cfg);
		
		// ADDRESS
		
		var addressGenerator = new ExampleDataGenerator<>(Address.class, LocalDateTime.now());
		
		addressGenerator.setData(Address::setCity, DataType.CITY);
		addressGenerator.setData(Address::setAddress, DataType.ADDRESS);
		
		List<Address> addressList = addressGenerator.create(clientsQuantity, new Random().nextInt());
		
		// CLIENTS
		
		var clientGenerator = new ExampleDataGenerator<>(Client.class, LocalDateTime.now());	
		
		clientGenerator.setData(Client::setName, DataType.FIRST_NAME);
		clientGenerator.setData(Client::setPhone, DataType.PHONE_NUMBER);
		clientGenerator.setData(Client::setEmail, DataType.EMAIL);
		
		List<Client> clientList = clientGenerator.create(clientsQuantity, new Random().nextInt());
		
		for (int i = 0; i < clientList.size(); i++) {
			
			Address ad = addressList.get(i);
			Client cl = clientList.get(i);
			
			ad.setMain(true);
			cl.setAddress(ad);
			ad.setClient(cl);
			cl.setPassword(passwordEncoder().encode(defaultPassword));
			
			clientRepository.save(cl);
			
		}
		
		// WORKERS
		
		var workerGenerator = new ExampleDataGenerator<>(Worker.class, LocalDateTime.now());	
		
		workerGenerator.setData(Worker::setOrderManager, DataType.BOOLEAN_50_50);
		workerGenerator.setData(Worker::setDeliverManager, DataType.BOOLEAN_50_50);
		workerGenerator.setData(Worker::setProductManager, DataType.BOOLEAN_50_50);
		workerGenerator.setData(Worker::setAdminSfCfg, DataType.BOOLEAN_50_50);
		workerGenerator.setData(Worker::setName, DataType.FIRST_NAME);
		workerGenerator.setData(Worker::setPhone, DataType.PHONE_NUMBER);
		workerGenerator.setData(Worker::setEmail, DataType.EMAIL);
		
		List<Worker> workerList = workerGenerator.create(workersQuantity, new Random().nextInt());
		
		workerList.forEach(w -> w.setPassword(passwordEncoder().encode(defaultPassword)));
		
		workerRepository.saveAll(workerList);
		
		// PRODUCTS
		
		var productGenerator = new ExampleDataGenerator<>(Product.class, LocalDateTime.now());
		
		productGenerator.setData(Product::setName, DataType.FOOD_PRODUCT_NAME);
		productGenerator.setData(Product::setDescription, DataType.SENTENCE);
		productGenerator.setData(Product::setPrice, DataType.NUMBER_UP_TO_1000);
		productGenerator.setData(Product::setStock, DataType.NUMBER_UP_TO_100);
		productGenerator.setData(Product::setUseStock, DataType.BOOLEAN_50_50);
		
		List<Product> productList = productGenerator.create(workersQuantity, new Random().nextInt());
		
		for (Product p : productList) {
			p.setCloudId("null_image");
			p.setType(randomProductCategory());
		}
		
		productRepository.saveAll(productList);
		
		// ORDERS
		
		List<Order> orderList = new ArrayList<>();
		List<OrderLine> linesList = new ArrayList<>();
		
		for (int k = 0; k < ordersQuantity; k++) {
			Order or = new Order();
			or.setClient(randomClient(clientList));
			or.setWorker(randomWorker(workerList));
			randomOrderLines(productList, or, new Random().nextInt((maxLinesPerOrder-1))+1).forEach(
					line -> or.setOrderLine(line)
					);
			or.setPaid(new Random().nextInt(2) == 1);
			or.setDelivered(new Random().nextInt(2) == 1);
			or.setDeliveryAddress(or.getClient().getAddresses().get(0));
			or.setDeliveryTime(randomDeliverTime(cfg.getAvailableDeliveries()));
			or.setTimestamp(randomTimeStamp());
			orderList.add(or);
		}
		
		orderRepository.saveAll(orderList);
		
		// MAIN TEST CLIENT
		
		Client cli1 = new Client();
		Address add1 = new Address("Barelona", "Carrer Figuera, 48");
		Address add1_2 = new Address("Girona", "Plç. Catalunya, 123");

		cli1.setName("Paula");
		cli1.setPhone("606624414");
		cli1.setEmail("hola@caracola.com");
		cli1.setPassword("pass123");
		cli1.setPassword(passwordEncoder().encode(cli1.getPassword()));
		cli1.setAddress(add1);
		cli1.setAddress(add1_2);
		
		add1.setClient(cli1);
		add1_2.setClient(cli1);
		add1.setMain(true);
		add1_2.setMain(false);
		
		clientRepository.save(cli1);
		
		// MAIN TEST WORKER
		
		Worker wrk1 = new Worker();
		
		wrk1.setOrderManager(true);
		wrk1.setProductManager(true);
		wrk1.setAdminSfCfg(true);
		wrk1.setDeliverManager(true);
		wrk1.setName("Raul");
		wrk1.setPhone("643923654");
		wrk1.setEmail("work@work.com");
		wrk1.setPassword("pass123");
		wrk1.setPassword(passwordEncoder().encode(wrk1.getPassword()));
		
		workerRepository.save(wrk1);

		// MAIN TEST PRODUCTS
				
		Product pro1 = new Product();
		Product pro2 = new Product();
		Product pro3 = new Product();
		Product pro4 = new Product();
		Product pro5 = new Product();
		Product pro6 = new Product();
		Product pro7 = new Product();
		Product pro8 = new Product();

		pro1.setName("Spring Burger");
		pro1.setDescription("When the cheese comes out everybodys happy lancashire cheese triangles");
		pro1.setStock(14);
		pro1.setPrice(1295);
		pro1.setUseStock(false);
		pro1.setCloudId("risiyveoolfkddy2b3vo");
		pro1.setType("burger");
		pro1.setActive(true);
		
		pro2.setName("Spring Pizza");
		pro2.setDescription("Ecocinni squirty cheese manchego mozzarella fondue cottage cheese taleggio");
		pro2.setStock(6);
		pro2.setPrice(900);
		pro2.setUseStock(true);
		pro2.setCloudId("bwdixrmxlfztzbjpgukc");
		pro2.setType("pizza");
		pro2.setActive(true);
		
		pro3.setName("Spring Soda");
		pro3.setDescription("Stinking bishop taleggio cheese and wine fondue cottage cheese taleggio comes out everybodys happy");
		pro3.setStock(30);
		pro3.setPrice(150);
		pro3.setUseStock(true);
		pro3.setCloudId("oabhdwhguexgt01mwmza");
		pro3.setType("drink");
		pro3.setActive(true);

		pro4.setName("Spring Salad");
		pro4.setDescription("Jarlsberg red leicester cheese triangles. Pepper jack monterey jack cauliflower cheese  cheese strings");
		pro4.setStock(0);
		pro4.setPrice(2125);
		pro4.setUseStock(false);
		pro4.setCloudId("gotm6box4zef6ku80d9y");
		pro4.setType("salad");
		pro4.setActive(true);

		pro5.setName("Spring Not Using Stock");
		pro5.setDescription("I don't care about stock!");
		pro5.setStock(0);
		pro5.setPrice(125);
		pro5.setUseStock(true);
		pro5.setCloudId("k8ntwlbsroxnpkyazna0");
		pro5.setType("burger");
		pro5.setActive(true);

		pro6.setName("Venga Vegan Pizza");
		pro6.setDescription("Leicester cheese triangles. Pepper jack monterey jack cauliflower cheese macaroni cheese fondue cheddar");
		pro6.setStock(11);
		pro6.setPrice(1337);
		pro6.setUseStock(true);
		pro6.setCloudId("rwu47rmn1keq7q98o0e8");
		pro6.setType("pizza");
		pro6.setActive(true);
		
		pro7.setName("Pasta Al-Pastorinni");
		pro7.setDescription("Manchego mozzarella fondue cottage taleggio macaroni cheese fondue cheddar danish fontina tagliattello");
		pro7.setStock(9);
		pro7.setPrice(1100);
		pro7.setUseStock(true);
		pro7.setCloudId("jait18jwpkhb2clbsnrw");
		pro7.setType("pasta");
		pro7.setActive(true);
		
		pro8.setName("La Green Burger");
		pro8.setDescription("Plant based burger is for everyone, it's been decades in the making!");
		pro8.setStock(0);
		pro8.setPrice(1195);
		pro8.setUseStock(false);
		pro8.setCloudId("null_image");
		pro8.setType("burger");
		pro8.setActive(true);
		
		productRepository.save(pro1);
		productRepository.save(pro2);
		productRepository.save(pro3);
		productRepository.save(pro4);
		productRepository.save(pro5);
		productRepository.save(pro6);
		productRepository.save(pro7);
		productRepository.save(pro8);
		
	}
	
	private String randomDeliverTime(Set<LocalTime> availableDeliveries) {
		List<LocalTime> times = availableDeliveries.stream().collect(Collectors.toList());
		LocalTime randomTime = times.get(new Random().nextInt(times.size()));
		return LocalTime.of(randomTime.getHour(), randomTime.getMinute()).toString();
	}

	private String randomProductCategory() {
				
		switch (new Random().nextInt(5)) {
		case 0:
			return "burger";
		case 1:
			return "pizza";
		case 2:
			return "pasta";
		case 3:
			return "drink";
		case 4:
			return "salad";
		default:
			return "no_category";
		}
				
	}
	
	private LocalDateTime randomTimeStamp() {
		
		int rnd = new Random().nextInt(7);
		int minusDays = (rnd*2)-rnd;
		int hours = new Random().nextInt(25);
		
		return LocalDateTime.now().minusDays(minusDays).plusHours(hours);
		
	}
	
	private List<OrderLine> randomOrderLines(List<Product> productList, Order order, int linesQuanitity) {
		
		List<OrderLine> lines = new ArrayList<>();
		
		for (int i = 0; i < linesQuanitity; i++) {
			OrderLine ol = new OrderLine();
			ol.setOrder(order);
			ol.setProduct(productList.get(new Random().nextInt(productList.size())));
			ol.setQuantity(new Random().nextInt(11)+1);
			lines.add(ol);
		}
		
		return lines;
		
	}
	
	private Client randomClient(List<Client> clientList) {
		
		return clientList.get(new Random().nextInt(clientList.size()));
		
	}
	
	private Worker randomWorker(List<Worker> workerList) {
		
		return workerList.get(new Random().nextInt(workerList.size()));
		
	}
}
