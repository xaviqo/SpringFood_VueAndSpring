package xavi.tech.springfood.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import xavi.tech.springfood.Globals;
import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;
import xavi.tech.springfood.utils.SFUtils;

@SuppressWarnings("rawtypes")
@Service
public class CloudinaryService{

	private Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
			"cloud_name", Globals.CLOUD_API_NAME,
			"api_key", Globals.CLOUD_API_KEY,
			"api_secret", Globals.CLOUD_API_SEC,
			"secure", true));


	public Map cloudUpload(MultipartFile multipartFile) {
		
		Map result = null;
		BufferedImage buffImg = null;

		try {
			buffImg = ImageIO.read(multipartFile.getInputStream());

			if (Objects.nonNull(buffImg)) {

				File file = SFUtils.mpfToFile(multipartFile);
				result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
				file.delete();

			} else {
				throw new SpringFoodException(SpringFoodError.CloudinaryNullImg,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.CloudinaryUploadError,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}

	public Map cloudDelete(String id) {
		Map result = null;
		try {
			result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.CloudinaryDeleteError,HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		return result;
	}

	@NonNull
	public String idToMenuUrl(String id) {
		
		Map object = null;
		String uri = "";
		
		if (!id.isBlank()) {
			
			try {
				object = cloudinary.api().resource(id, ObjectUtils.emptyMap());
				uri = object.get("url").toString();
			} catch (Exception e) {
				throw new SpringFoodException(SpringFoodError.CloudinaryError,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
	
		return uri;
	}

}
;