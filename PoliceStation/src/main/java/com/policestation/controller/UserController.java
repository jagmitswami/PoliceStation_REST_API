package com.policestation.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.policestation.exception.UserException;
import com.policestation.model.Customer;
import com.policestation.model.Police;
import com.policestation.model.PoliceStation;
import com.policestation.service.CustomerService;
import com.policestation.service.PoliceService;

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
