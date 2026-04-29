package com.busbooking.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;
	private final JwtService jwtService;

	
	public JwtAuthFilter(UserDetailsService userDetailsService, JwtService jwtService) {
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
	    // ADD THESE 3 LINES HERE 👇
	    System.out.println("=== JWT FILTER ===");
	    System.out.println("PATH: " + request.getServletPath());
	    System.out.println("AUTH HEADER: " + request.getHeader("Authorization"));


		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			try {
				username = jwtService.extractUsername(token);
			} catch (Exception e) {
				 System.out.println("JWT parsing failed: " + e.getMessage());
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		    List<String> roles = jwtService.getRoles(token);

		    var authorities = roles.stream()
		        .map(role -> new SimpleGrantedAuthority(role)) 
		        .toList();

		    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		    if (jwtService.validateToken(token, userDetails)) {
		        UsernamePasswordAuthenticationToken authToken =
		            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

		        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		        SecurityContextHolder.getContext().setAuthentication(authToken);
		    }
		}
		filterChain.doFilter(request, response);
	}
}
