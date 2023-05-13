package com.policestation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.policestation.model.PoliceStation;
import com.policestation.service.PoliceService;

import jakarta.validation.Valid;

@RestController
public class AdminController {

	@Autowired
	PoliceService policeService;
	
	private static Integer adminId;
	private static String pass;
	
	static {
		adminId = 1234;
		pass = "asdf";
	}

	@PostMapping()
	public ResponseEntity<PoliceStation> registerPoliceSation(@Valid @RequestBody PoliceStation policeStation){
		
		return null;
		
	}
	
}
