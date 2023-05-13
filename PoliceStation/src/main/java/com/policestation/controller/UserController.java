package com.policestation.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.policestation.exception.FIRException;
import com.policestation.exception.PoliceException;
import com.policestation.exception.PoliceStationException;
import com.policestation.exception.UserException;
import com.policestation.model.Customer;
import com.policestation.model.FIR;
import com.policestation.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/policestation/users")
public class UserController {

	@Autowired 
	CustomerService customerService;
	
	@PostMapping("/register")
	public ResponseEntity<Customer> registerAsUser(@Valid @RequestBody Customer customer) throws UserException{
		
		return new ResponseEntity<>(customerService.registerUser(customer), HttpStatus.CREATED);
		
	}
	
	@PostMapping("/fileFIR/{policeOfficerId}/{policeStationId}")
	public ResponseEntity<FIR> fileFIR(@Valid @RequestBody FIR fir, @PathVariable("policeOfficerId") Integer policeOfficerId, @PathVariable("policeStationId") Integer policeStationId) throws PoliceException, PoliceStationException{
		
		return new ResponseEntity<>(customerService.fileFIR(fir, policeOfficerId, policeStationId), HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/withdrawFIR/{firId}")
	public ResponseEntity<String> withdrwaFIR(@PathVariable("firId") Integer firId) throws FIRException{
		
		return new ResponseEntity<>(customerService.withdrawFIR(firId), HttpStatus.OK);
		
	}
	
}
