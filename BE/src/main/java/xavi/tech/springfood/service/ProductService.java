package xavi.tech.springfood.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
			throw new SpringFoodException(SpringFoodError.ErrorCreatingProduct,"",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public ResponseEntity<?> editImg(String productId, MultipartFile img){
		
		Map cloudResult = null;
		int result = 0;
		
		try {
			cloudResult = cloudinaryService.cloudUpload(img);
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.InvalidImgFormat,"",HttpStatus.BAD_REQUEST);
		}
		
		try {
			result = productRepository.updateProductImage(Long.valueOf(productId), cloudResult.get("public_id").toString());
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.ErrorUpdatingProductImg,"",HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok().body(result);
	}
	
	public ResponseEntity<?> deleteImg(String cloudId){
		
		Map result = cloudinaryService.cloudDelete(cloudId);
		
		if ("ok".equals(result.get("result"))) {
			return ResponseEntity.ok().body(productRepository.nullizeImage(cloudId));
		} else {
			throw new SpringFoodException(SpringFoodError.ImageNotFound,"",HttpStatus.BAD_REQUEST);
		}
		
	}

	public ResponseEntity<?> getMenuProducts() {
		
		List<MenuProductProjection> menuProducts = productRepository.getMenuProducts();
		
		if (hasProducts(menuProducts)) {
			return ResponseEntity.ok().body(menuProducts);
		}
		
		throw new SpringFoodException(SpringFoodError.EmptyProductList,"",HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<?> getAll() {
		
		List<Product> products = productRepository.findAll();
		
		if (hasProducts(products)) {
			return ResponseEntity.ok().body(products);
		}
		
		throw new SpringFoodException(SpringFoodError.EmptyProductList,"",HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<?> editPrice(Map<String,String> prodAndPrice){
				
		long id = 0;
		long price = 0;
		
		try {
			id = Long.valueOf(prodAndPrice.get("id"));
			price = Long.valueOf(prodAndPrice.get("price"));
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.ErrorUpdProductPrice,"",HttpStatus.BAD_REQUEST);
		}
		
		if (productRepository.updateProductPrice(id,price) == 1) {
			return ResponseEntity.ok().body(1);
		} else {
			throw new SpringFoodException(SpringFoodError.ProductIdNotFound,"",HttpStatus.BAD_REQUEST);
		}
		

	}
	
	private boolean hasProducts(List<?> productList){
		return (productList != null && productList.size()>0);
	}
	

	
}
