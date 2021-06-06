package com.example.demo.security;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;


public class CSRFTokenConfig implements CsrfTokenRepository {
   
     final String DEFAULT_CSRF_COOKIE_NAME = "XSRF-TOKEN";

     final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";

     final String DEFAULT_CSRF_HEADER_NAME = "X-XSRF-TOKEN";
    
	public CSRFTokenConfig() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CsrfToken generateToken(HttpServletRequest request) {
		//String id = UUID.randomUUID().toString().replace("-", "");
		CsrfToken csrf =  new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", createNewToken());
	
		return csrf;
	}

	@Override
	public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
		  String tokenValue = token == null ? "" : token.getToken();
	        Cookie cookie = new Cookie(this.DEFAULT_CSRF_COOKIE_NAME, tokenValue);
	        cookie.setSecure(true);
	        cookie.setHttpOnly(true);
	        cookie.setMaxAge(300);
	        
	        response.addCookie(cookie);

	}

	@Override
	public CsrfToken loadToken(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, this.DEFAULT_CSRF_COOKIE_NAME);
        if (cookie == null) {
            return null;
        }
        String token = cookie.getValue();
        if (!StringUtils.hasLength(token)) {
            return null;
        }
        return new DefaultCsrfToken(this.DEFAULT_CSRF_HEADER_NAME, this.DEFAULT_CSRF_PARAMETER_NAME, token);
	}
	
	 private String createNewToken() {
		 return UUID.randomUUID().toString();
	       
	    }
	

}
