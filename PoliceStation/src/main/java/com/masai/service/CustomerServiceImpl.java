package com.masai.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.masai.exception.FIRException;
import com.masai.exception.LoginException;
import com.masai.exception.PoliceException;
import com.masai.exception.UserException;
import com.masai.model.Customer;
import com.masai.model.FIR;
import com.masai.model.PoliceStation;
import com.masai.repository.CustomerRepo;
import com.masai.repository.FIRRepo;
import com.masai.repository.PoliceRepo;
import com.masai.repository.PoliceStationRepo;

public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	PoliceRepo policeRepo;
	
	@Autowired
	PoliceStationRepo stationRepo;
	
	@Autowired
	FIRRepo firRepo;
	
	@Override
	public Customer registerUser(Customer customer) throws UserException {
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer resgisteredUser = customerRepo.findByPhone(phone).get();
		
		if(resgisteredUser != null) throw new UserException("User is already registered with this mobile number");
		
		customer.setRole("ROLE_USER");
		
		customer = customerRepo.save(customer);
		
		return customer;
	}

	@Override
	public FIR fileFIR(FIR fir) throws LoginException, UserException, PoliceException {
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = customerRepo.findByPhone(phone).get();
		
		fir.setApplicantId(customer.getUserId());
		
		PoliceStation policeStation = stationRepo.findById(fir.getPoliceStationId()).orElseThrow(()-> new PoliceException("Invalid police station id passed"));
		
		policeStation.getFirList().add(fir.getFirId());
		
		stationRepo.save(policeStation);
		
		return firRepo.save(fir);
		
	}

	@Override
	public String withdrawFIR(Integer firId) throws LoginException, UserException, FIRException {
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = customerRepo.findByPhone(phone).get();
		
		FIR fir = firRepo.findByApplicantIdAndFirId(customer.getUserId(), firId);
		
		if(LocalDateTime.now().isAfter(fir.getTimeStamp().plusHours(24))){
			throw new FIRException("Time limit over to withraw FIR");
		}
		
		if(!fir.isOpen()) {
			throw new FIRException("FIR already closed");
		}
		
		firRepo.delete(fir);
		
		return "FIR withrawl successfull";
	}

	

}
