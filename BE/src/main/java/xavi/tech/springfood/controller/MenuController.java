package xavi.tech.springfood.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.service.ProductService;
import xavi.tech.springfood.service.SfConfigService;

@SuppressWarnings("rawtypes")
@AllArgsConstructor
@RestController
@PreAuthorize("permitAll()")
@RequestMapping(path="/api/menu/product")
public class MenuController {
	
	private final ProductService productService;
	private final SfConfigService configService;
	
	@GetMapping(path="/getAll")
	public ResponseEntity<?> getAllProducts() {
		return productService.getMenuProducts();
	}
	
	@GetMapping(path="/getDeliveryTimes")
	public ResponseEntity<?> getDeliveryTimes() {
		return configService.getAllDeliveryTimes();
	}
	
	@PostMapping(path="/setOpenTime")
	public ResponseEntity<?> setOpenTime(@RequestBody Map<String,Integer> hm) {
		return configService.setOpenTime(hm.get("h"),hm.get("m"));
	}
	
	@PostMapping(path="/setCloseTime")
	public ResponseEntity<?> setCloseTime(@RequestBody Map<String,Integer> hm) {
		return configService.setCloseTime(hm.get("h"),hm.get("m"));
	}

}
