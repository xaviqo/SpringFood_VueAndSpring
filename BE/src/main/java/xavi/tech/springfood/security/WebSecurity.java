package xavi.tech.springfood.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

	
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:1337","https://localhost:1337"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

	@Override
	public void configure(HttpSecurity http) throws Exception {

		// ONLY USE COMENTED LINES WITH @EnableWebSecurity

		http
		.cors()//.configurationSource(corsConfigurationSource())
		.and()
		.csrf().disable()
		.authorizeRequests().antMatchers(
				"/api/account/**",
				"/api/menu/**"
				).permitAll()
		//            .and()
		//            .authorizeRequests().antMatchers("/api/admin/**").hasRole("ADMIN")
		//            .and()
		//            .authorizeRequests().antMatchers("/api/user/**").hasRole("USER")
		.and()
		.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.logout().permitAll().logoutUrl("/api/account/logout")
		.clearAuthentication(true)
		.invalidateHttpSession(true)
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
