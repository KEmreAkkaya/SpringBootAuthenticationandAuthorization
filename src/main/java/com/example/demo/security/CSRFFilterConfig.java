package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
//import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CSRFFilterConfig extends OncePerRequestFilter{


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		if(request.getMethod().equals("GET") || request.getMethod().equals("POST")) 
		{
//			CsrfToken csrf= (CsrfToken) request.getAttribute("_csrf");
//			
//			System.out.println("Parameter name: " +csrf.getParameterName());
//			System.out.println("Token: " +csrf.getToken());
			filterChain.doFilter(request, response); 
			
		}
		else 
		{
			 response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		

	
	}

}
