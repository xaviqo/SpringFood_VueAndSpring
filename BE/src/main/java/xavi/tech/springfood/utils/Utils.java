package xavi.tech.springfood.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Utils {
	
	public static String timestampToHMString(Timestamp timestamp) {
		return new SimpleDateFormat( "HH:mm" )
				.format(timestamp);
	}
	
	public static Double roundTotalAmount(Double totalAmount) {
		DecimalFormat df = new DecimalFormat("#.##");  
		return Double.valueOf(df.format(totalAmount));
	}

}
