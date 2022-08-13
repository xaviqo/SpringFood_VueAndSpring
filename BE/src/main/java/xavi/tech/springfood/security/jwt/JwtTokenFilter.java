package xavi.tech.springfood.security.jwt;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.AllArgsConstructor;
import xavi.tech.springfood.security.UserDetailsServiceImpl;

@AllArgsConstructor
@Service
public class JwtTokenFilter extends OncePerRequestFilter {
	
	private final JwtUtils jwtUtil;
	private final UserDetailsServiceImpl detailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authorizationHeader = request.getHeader("Authorization");
		
		if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith("Bearer") || authorizationHeader.isEmpty()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = authorizationHeader.split(" ")[1].trim();	
		
		if (!jwtUtil.validate(token)) {
			filterChain.doFilter(request, response);;
			return;
		}
		
		String email = jwtUtil.getUsername(token);
		UserDetails userDetails = detailsService.loadUserByUsername(email);
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
		authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContextHolder.getContext().setAuthentication(authToken);
		
		filterChain.doFilter(request, response);

		
	}
	
	

}
