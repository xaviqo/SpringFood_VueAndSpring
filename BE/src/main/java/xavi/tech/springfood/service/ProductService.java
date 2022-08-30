package xavi.tech.springfood.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.dto.ProductDTO;
import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;
import xavi.tech.springfood.model.Product;
import xavi.tech.springfood.projection.MenuProductProjection;
import xavi.tech.springfood.repository.ProductRepository;

@SuppressWarnings("rawtypes")
@AllArgsConstructor
@Service
public class ProductService{

	private final CloudinaryService cloudinaryService;
	private final ProductRepository productRepository;
	
	public ResponseEntity<?> createProduct(ProductDTO dto) {
		
		System.out.println();
		Product product = null;

		Map cloudResult = cloudinaryService.cloudUpload(dto.getImg());
		dto.setCloudId(cloudResult.get("public_id").toString());
		product = ProductDTO.dtoToProduct(dto);
		
		if (productRepository.save(product) != null) {
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} else {
			throw new SpringFoodException(SpringFoodError.ErrorCreatingProduct,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	public ResponseEntity<?> getMenuProducts() {
		
		List<MenuProductProjection> menuProducts = productRepository.getMenuProducts();
		
		if (hasProducts(menuProducts)) {
			return ResponseEntity.ok().body(menuProducts);
		}
		
		throw new SpringFoodException(SpringFoodError.EmptyProductList,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<?> getAll() {
		
		List<Product> menuProducts = productRepository.findAll();
		
		if (hasProducts(menuProducts)) {
			return ResponseEntity.ok().body(menuProducts);
		}
		
		throw new SpringFoodException(SpringFoodError.EmptyProductList,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private boolean hasProducts(List<?> productList){
		return (productList != null && productList.size()>0);
	}
	

	
}
