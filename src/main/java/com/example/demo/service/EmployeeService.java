package com.example.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;


import com.example.demo.model.Employee;


public interface EmployeeService {
	public List<Employee> getAllEmployees();
	public void saveEmployee(Employee employee);
	public Employee getEmployeeById(long id);
	public String deleteEmployeeById(long id);
	//public Employee getEmployeeRoles(long id);
	public UserDetails loadUserByUsername(String username);
	//public void register(Employee user);
    public boolean checkUsername (Employee employee);
    
	}


