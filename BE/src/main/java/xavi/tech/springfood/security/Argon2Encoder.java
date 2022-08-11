package xavi.tech.springfood.security;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@SuppressWarnings("deprecation")
public class Argon2Encoder {
	
	private static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

	
	public static String passwordToHash(String password) {
		return argon2.hash(1,1024,1,password);	
	}

	public static boolean checkHashAndPassword(String hash, String password) {
		return argon2.verify(hash, password);
	}

}
