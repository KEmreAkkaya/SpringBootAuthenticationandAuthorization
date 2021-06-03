package com.example.demo.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

@Controller
public class EmployeeController implements ErrorController {
    
	
	private static int WarningScreenControl=0;

	
	@Autowired
    private EmployeeService employeeService;
    
	@RequestMapping("/")
	public String viewHomePage(Model model ) 
	{
		
		return "index";
	}

	@RequestMapping("/login")
	public String viewLoginPage(Model model, HttpServletRequest request ) 
	{   
		
//		if(result.hasErrors()) 
//		{
//			
//			return "login";
//		}
//		model.addAttribute("employee",employee);
//		System.out.println("employeeeee : "+ employee.getUsername());
//		Cookie cookies = new Cookie("Username", employee.getUsername());
//		response.addCookie(cookies);
//		cookies.setMaxAge(5*60);
//		
		
		Cookie[] cookie = request.getCookies();
		
		for (Cookie temp: cookie)
		{
			
			if("JSESSIONID".equals(temp.getName()))
		        System.out.println("Cookie :" + temp.getValue());
			   
			
		}
		
		     
		return "login";
	}
	
	@RequestMapping("/log-in")
	public String viewLoginPxage(Model model ) 
	{
		
		return "log-in";
	}

	@RequestMapping("/sign-up")
	public String viewSignUpPage(Model model ) 
	{
		
		return "sign-up";
	}
  
	
	@RequestMapping("/adminpanel")
	public String viewAdminPage(Model model ) 
	{   
		if(  WarningScreenControl==1) 
		{
			model.addAttribute("deleteMessage","Username succesfully deleted.");
		}
		if( WarningScreenControl==2) {
		    model.addAttribute("errorMessage","Username already exists.");
		}
		
		
		
		Employee employee=new Employee();
		model.addAttribute("listEmployees",employeeService.getAllEmployees());
		model.addAttribute("employee",employee);
		
		 WarningScreenControl=0;
		
		return "adminpanel";
	}
	
	@GetMapping("/adminpanel/add")
	public String viewAddPage(Model model ,Employee employee) 
	{
		//model.addAttribute("employee",employee);
		return "add";
	}
	
	@PostMapping("/adminpanel/add")
	public String saveAddPage(Model model ,@Valid Employee employee,Errors error) 
	{
		
        if(error.hasErrors()) 
        {
        	//model.addAttribute("message","Registration Error ");
        	return "add";
        }
        else {
       
        if(!employeeService.checkUsername(employee)) {
		model.addAttribute("employee",employee);
	    employeeService.saveEmployee(employee);
	    model.addAttribute("message","Registration succesfully");
		return "add";
        }
        
        else 
        {
        	
        	WarningScreenControl=2;
        	return "redirect:/adminpanel";
        }
        }
	}
	

	
	@PostMapping("/adminpanel/updateEmployee")
	public String updateEmployee(Model model ,@Valid Employee employee,Errors error) 
	{   
		if(error.hasErrors()) 
        {
        	//model.addAttribute("message","Registration Error ");
			System.out.println("ERRORRRRR! : " +error.hasErrors());
        	return "update";
        }
        else {
       
		model.addAttribute("employee",employee);
	    employeeService.saveEmployee(employee);
	    model.addAttribute("message","User succesfully updated.");
		return "update";
        }
		
	}
	
	

	@RequestMapping("/adminpanel/updateEmployee/{id}")
	public String updateEmployee(@PathVariable (value="id") long id, Model model) 
	{
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee",employee);
		 WarningScreenControl=3;
		return "update";   
		
	}
	
	@GetMapping("/adminpanel/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value="id") long id, Model model) 
	{
		this.employeeService.deleteEmployeeById(id);
		
		WarningScreenControl=1;
		return "redirect:/adminpanel";   
		
	}
	
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	   
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	    
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	            return "error-404";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	            return "error-500";
	        }
	        else if(statusCode == HttpStatus.FORBIDDEN.value()) {
	            return "error-403";
	        }
	    }
	    return "error";
	}

	@Override
	public String getErrorPath() {
		
		return 	"/error";
	}
   
	


}
