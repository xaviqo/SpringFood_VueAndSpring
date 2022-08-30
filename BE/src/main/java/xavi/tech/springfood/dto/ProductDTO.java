package xavi.tech.springfood.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xavi.tech.springfood.model.Product;
import xavi.tech.springfood.projection.MenuProductProjection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	
	private String description;
	private String name;
	private long price;
	private long stock;
	private boolean useStock;
	private String type;
	private MultipartFile img;
	private String cloudId;
	
	public static ProductDTO projectionToMenuDto(MenuProductProjection projection, String imgUri) {
		
		ProductDTO dto = new ProductDTO();
		
		try {
			dto.setImg(null);
			dto.setCloudId(projection.getCloudId());
			dto.setName(projection.getName());
			dto.setDescription(projection.getDescription());
			dto.setPrice(projection.getPrice());
			dto.setType(projection.getType());
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
		return dto;
		
	}
	
	public static Product dtoToProduct(ProductDTO dto) {
		
		Product product = new Product();
		
		try {
			
			product.setName(dto.getName());
			product.setDescription(dto.getDescription());
			product.setPrice(dto.getPrice());
			product.setUseStock(dto.isUseStock());
			product.setStock(dto.getStock());
			product.setCloudId(dto.getCloudId());
			product.setType(dto.getType().toLowerCase());
		
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
		return product;
	}

}
