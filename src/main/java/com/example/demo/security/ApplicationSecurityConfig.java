package com.example.demo.security;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


//Implement for testing username ,password ,roles 

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter 
{  
	
	@Resource
    private UserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setHideUserNotFoundExceptions(false);
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
        
     
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
	
	@Autowired
	CSRFFilterConfig filter ; 
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{  		   
	 
		http
		   .headers()
		      .frameOptions()
		         .sameOrigin();

	   
	   http.headers()
	       .xssProtection()
	       .and()
	       .contentSecurityPolicy("script-src 'self'");
	   http
	        .sessionManagement()
	             .maximumSessions(1)
	             .and()
	             .sessionFixation().none()
	             .invalidSessionUrl("/login");
	   //Disable for testing
//	   http.csrf().disable();
	   
	   
	   http
	        .csrf().csrfTokenRepository(new CSRFTokenConfig());
	   http
	        .addFilterAfter(filter, CsrfFilter.class);
	   
	   http
	        .authorizeRequests()
	        .antMatchers("/login, /","/css/**","/js/**").permitAll()
	        .and()
	        .authorizeRequests()
            .antMatchers("/adminpanel","/adminpanel/**").hasAuthority("ADMIN")
       //USE FOR TESTING USERNAME ROLE
            .antMatchers("/adminpanel","/adminpanel/**").hasRole("ADMIN")
            .anyRequest().authenticated()    
	        .and()        
	        .formLogin()
	        .loginPage("/login").permitAll()
	        .defaultSuccessUrl("/", true)
	        .and()
	        .rememberMe()
	                   .userDetailsService(this.userDetailsService)	                   
	                   .tokenValiditySeconds(5*60)
	                   .key("helloworld")
	                   .rememberMeCookieName("remember-me")
	                   .useSecureCookie(true)
	                   
	        .and()
	        .logout()
	                   .logoutUrl("/logout")
	                   .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
	                   .clearAuthentication(true)
	                   .invalidateHttpSession(true)
	                   .deleteCookies("JSESSIONID","remember-me","XSRF-TOKEN")
	                   .logoutSuccessUrl("/login");
          
	
	   
	  
	                   
	
	}
	
	
	//TESTING USERNAME ,PASSWORD ,ROLES
	@Override
	@Bean
	protected UserDetailsService userDetailsService() 
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder.encode("123456Ea"))
				.roles("ADMIN")
				.build();
		
		UserDetails user = User.builder()
				.username("user")
				.password(passwordEncoder.encode("123456Ea"))
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(admin,user);
	
		
	}
	
	

	
	
	 

}
