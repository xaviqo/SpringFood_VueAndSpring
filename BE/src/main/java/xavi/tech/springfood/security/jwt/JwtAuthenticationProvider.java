package xavi.tech.springfood.security.jwt;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.exception.SpringFoodError;
import xavi.tech.springfood.exception.SpringFoodException;
import xavi.tech.springfood.security.UserDetailsServiceImpl;

@AllArgsConstructor
@Service
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final UserDetailsServiceImpl userDetailsService;
	private final BCryptPasswordEncoder encoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String email = String.valueOf(authentication.getPrincipal());
		String password = String.valueOf(authentication.getCredentials());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		
		if (Objects.nonNull(userDetails)) {
			if (encoder.matches(password, userDetails.getPassword())) {
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
				return token;
			}
		}
		
		throw new SpringFoodException(SpringFoodError.InvalidUser,HttpStatus.UNAUTHORIZED);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
