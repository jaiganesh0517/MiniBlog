package com.jai.MiniBlog.config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jai.MiniBlog.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	private JwtUtil jwt;
	
	public JwtFilter(JwtUtil jwt) {
		this.jwt = jwt;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request , HttpServletResponse response,FilterChain filterChain)
			throws ServletException ,IOException {
		 String authHeader = request.getHeader("Authorization");
		 if(authHeader != null && authHeader.startsWith("Bearer ")){
			 String token = authHeader.substring(7);
			 int userId = jwt.extractUserId(token);
			 
			 UsernamePasswordAuthenticationToken authToken = 
					 new UsernamePasswordAuthenticationToken(userId,null,new ArrayList<>());
			 SecurityContextHolder.getContext().setAuthentication(authToken);
			 
		 }
		 filterChain.doFilter(request, response);
	}
}
