package xavi.tech.springfood.controller.admin;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.dto.ProductDTO;
import xavi.tech.springfood.service.CloudinaryService;
import xavi.tech.springfood.service.ProductService;

@SuppressWarnings("rawtypes")
@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(path="/api/admin/product")
public class AdminProductController {

	private final CloudinaryService cloudinaryService;
	private final ProductService productService;
	

	@PostMapping(path="/createProduct")
	public ResponseEntity<?> createProduct(@ModelAttribute ProductDTO productDto){
		return productService.createProduct(productDto);
	}

	@DeleteMapping(path="/deleteImg/{imgId}")
	public ResponseEntity<Map> productDelete(@PathVariable String imgId) {
		Map result = cloudinaryService.cloudDelete(imgId);
		return new ResponseEntity<Map>(result,HttpStatus.OK);
	}

}
