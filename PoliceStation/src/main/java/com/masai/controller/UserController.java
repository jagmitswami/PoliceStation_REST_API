package com.masai.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.UserException;
import com.masai.model.Police;
import com.masai.model.PoliceStation;
import com.masai.model.Customer;
import com.masai.service.PoliceService;
import com.masai.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/home")
public class UserController {

	@Autowired 
	CustomerService customerService;
	
	@Autowired
	PoliceService policeService;
	
	
	@PostMapping("/users/register/user")
	public ResponseEntity<Customer> registerAsUser(@Valid @RequestBody Customer customer) throws UserException{
		return new ResponseEntity<>(customerService.registerUser(customer), HttpStatus.CREATED);
	}
	
	@PostMapping("/users/register/police")
	public ResponseEntity<Police> registerAsPolice(@Valid @RequestBody Police police) throws UserException{
		return new ResponseEntity<>(policeService.registerPolice(8080, police), HttpStatus.CREATED);
	}
	
	@PostMapping("/users/register/policeStation")
	public ResponseEntity<PoliceStation> registerPoliceStation(@Valid @RequestBody PoliceStation policeStation){
		return new ResponseEntity<>(policeService.registerPoliceStation(8080, policeStation), HttpStatus.CREATED);
	}
	
	@GetMapping("/users")
	public String get() {
		return "Yes";
	}
}
