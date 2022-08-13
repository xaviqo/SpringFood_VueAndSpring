package xavi.tech.springfood.security;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.model.Account;
import xavi.tech.springfood.repository.AccountRepository;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private final AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Account account = accountRepository.findByEmail(email);
		
		if (Objects.nonNull(account)) {
			return new User(account.getEmail(), account.getPassword(), Arrays.asList(new SimpleGrantedAuthority(account.getRole().getDescription())));
		}
		throw new UsernameNotFoundException(email);
	}
	
	

}
