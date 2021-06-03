package com.example.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;


@Service
@ControllerAdvice
public class EmployeeServiceImpl implements EmployeeService,UserDetailsService{


	
    @Autowired
    private EmployeeRepository employeeRepository;
    
	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public void saveEmployee(Employee employee) {
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
		//bCryptPasswordEncoder = new  BCryptPasswordEncoder(10);
		//System.out.println("PASSWORD CLEAR TEXT :"+ employee.getPassword());
		employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
		//System.out.println("PASSWORD CRYPT TEXT :"+ employee.getPassword());
		
		this.employeeRepository.save(employee);
	}

	@Override
	public Employee getEmployeeById(long id) 
	{
		Employee employee=null;
		Optional<Employee> optional= employeeRepository.findById(id);
		
		if(optional.isPresent()) 
		{
			
			employee= optional.get();
			//System.out.println("Employee found .It is :"+ employee.getUsername());
		}
		else {
			throw new RuntimeException("Employee Not found:"+id);
			
			
		}
		return employee;
	}

	@Override
	public String deleteEmployeeById(long id) 
	{
		this.employeeRepository.deleteById(id);
		return "User succesfully deleted";
	}


	@Override
	public boolean checkUsername(Employee employee) {
	  	
		if(employeeRepository.findByUsername(employee.getUsername()) != null)
	    {
	    
	    	return true;  	  
	    }
	    else 
	    {    	
	    	return false;    	
	    }
	
				
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		final Employee employee = employeeRepository.findByUsername(username);
		if(employee != null)
        {
			//UserInfo userInfo = userDAO.getUserInfo(username);
			GrantedAuthority authority = new SimpleGrantedAuthority(employee.getRoles());
			UserDetails userDetails = (UserDetails)new User(employee.getUsername(), 
					employee.getPassword(), Arrays.asList(authority));
			return userDetails;
        }
		else {
			
			 throw new 
	            UsernameNotFoundException("User not exist with name :" +employee);
		}
		
		
	}

	
	

  
	
	

	

}
