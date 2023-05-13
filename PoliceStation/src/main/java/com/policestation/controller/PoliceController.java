package com.policestation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.policestation.exception.FIRException;
import com.policestation.exception.UserException;
import com.policestation.model.PoliceDTO;
import com.policestation.service.PoliceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/policestation/police")
public class PoliceController {
	
	@Autowired
	PoliceService policeService;
	
	//any
	@PostMapping("/register/{verficationId}")
	public ResponseEntity<PoliceDTO> registerPolice(@PathVariable("verficationId") Integer verficationId, @Valid @RequestBody PoliceDTO policeDTO) throws UserException{
		
		return new ResponseEntity<>(policeService.registerPolice(verficationId, policeDTO), HttpStatus.CREATED);
		
	}
	
	//sho
	@GetMapping("/closedMaxCases")
	public ResponseEntity<PoliceDTO> closedMaxCasesPolice() throws UserException{
		
		return new ResponseEntity<>(policeService.closedMaxCases(), HttpStatus.OK);
		
	}
	
	//police
	@PutMapping("closeCase/{firId}")
	public ResponseEntity<String> closeCaseByPolice(@PathVariable("firId") Integer firId) throws FIRException{
		
		return new ResponseEntity<>(policeService.closeCase(firId), HttpStatus.OK);
		
	}
	
}
