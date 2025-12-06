package com.example.To_Do.security;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.To_Do.Entity.User;
import com.example.To_Do.repo.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	
	
	
	
	
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String auth = request.getHeader("Authorization");
		String token = null;
		String email = null;
		
		if(auth != null && auth.startsWith("Bearer ")) {
			token = auth.substring(7);
			email = jwtUtil.extractUsername(token, claims->claims.getSubject());
			
		}
		
		if(email != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			
			if(jwtUtil.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
			}
			
		}
		
		filterChain.doFilter(request, response);
		
		
	}
	
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();
		
		return path.equals("/login") || path.equals("/register");
	}
	
	
	
	
	
	

}
