package xavi.tech.springfood.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;

@SuppressWarnings("deprecation")
public class SFUtils {
	
	public static String timestampToHMString(Timestamp timestamp) {
		return new SimpleDateFormat( "HH:mm" )
				.format(timestamp);
	}
	
	public static File mpfToFile(MultipartFile multipartFile) throws IOException {
		File file = null;
		try {
			file = new File(multipartFile.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(multipartFile.getBytes());
			fos.close();
		} catch (Exception e) {
			throw new SpringFoodException(SpringFoodError.CloudinaryMpfParse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return file;
	}
}
