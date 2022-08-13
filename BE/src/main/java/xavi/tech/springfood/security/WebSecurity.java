package xavi.tech.springfood.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.security.jwt.JwtAuthenticationProvider;
import xavi.tech.springfood.security.jwt.JwtTokenFilter;

@SuppressWarnings("deprecation")
@AllArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	private final JwtTokenFilter jwtTokenFilter;
	private final JwtAuthenticationProvider authenticationProvider;
	
	
	
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception{
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		// ONLY USE COMENTED LINES WITH @EnableWebSecurity
		
		http
			.csrf().disable()
			.authorizeRequests().antMatchers("/api/account/**").permitAll()
//            .and()
//            .authorizeRequests().antMatchers("/api/admin/**").hasRole("ADMIN")
//            .and()
//            .authorizeRequests().antMatchers("/api/user/**").hasRole("USER")
			.anyRequest().authenticated()
			.and()
			.exceptionHandling()
            .accessDeniedHandler(new CustomAccessDeniedHandler())
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http
			.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
			
		
	}
	

}
