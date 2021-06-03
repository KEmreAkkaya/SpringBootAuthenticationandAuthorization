package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.PropertySource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@PropertySource(value = "classpath:application-${env}.properties", encoding = "UTF-8")
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
    
    
    @NotNull(message = "Firstname can not be empty.")
    @Size(min=3,max=16, message="Size must be 3 -16 characters.")
    @Pattern(regexp="^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$",message="Name is not valid")
    
    @Column(name="first_name")
	private String firstName;
    
    @NotNull(message = "Surname can not be empty.")
    @Size(min=3,max=16, message="Size must be 3 -16 characters.")
    @Pattern(regexp="^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$",message="Lastname is not valid")
    @Column(name="last_name")
	private String lastName;
    

    @NotNull(message = "Username cant be empty.")
    @Size(min=3,max=16, message="Username must be 3 -16 characters.")
    @Pattern(regexp="^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,16}[a-zA-Z0-9]$",message="Username is not valid")
    @Column(name="username")
   	private String username;
    
    @NotNull(message = "Password cant be empty.")
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,60}$",message="Password must include one letter,one number ,one big letter")
    @Column(name="password")
   	private String password;
    
    @NotNull(message="Email cant be empty.")
    @Email(regexp="^(.+)@(.+)$" , message="Email is not valid.")
    @Column(name="email")
	private String email;
    
    @NotNull(message="Phone number can not be empty.")
    //@Size(min=11,max=11 ,message="Phone number must include 11 digits")
    @Pattern(regexp="^\\d{11}$",message="Phone number must include 11 digits")
    @Column(name="phone")
    private String phone;
    
    
    @NotNull(message="Roles can not be empty.")
    @Column(name="roles")
    private String roles;
	
   
    @Column(name="enabled")
    private String enabled;
    
    
    public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	
	 
	public String getRoles() {
		return roles;
	}


	public void setRoles(String roles) {
		this.roles = roles;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
 
	public String getPhone() {
			return phone;
		}


	public void setPhone(String phone) {
			this.phone = phone;
		}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}




}
