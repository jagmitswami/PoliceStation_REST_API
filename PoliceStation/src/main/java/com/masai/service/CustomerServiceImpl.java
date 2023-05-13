package com.masai.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.masai.exception.FIRException;
import com.masai.exception.LoginException;
import com.masai.exception.PoliceException;
import com.masai.exception.PoliceStationException;
import com.masai.exception.UserException;
import com.masai.model.Customer;
import com.masai.model.FIR;
import com.masai.model.Police;
import com.masai.model.PoliceStation;
import com.masai.model.Status;
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
		
		customer.setRole("ROLE_CUSTOMER");
		
		customer = customerRepo.save(customer);
		
		return customer;
	}

	@Override
	public FIR fileFIR(FIR fir, Integer policeOfficerId, Integer policeStationId) throws LoginException, UserException, PoliceException, PoliceStationException {
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = customerRepo.findByPhone(phone).get();
		
		Police police = policeRepo.findById(policeOfficerId).orElseThrow(()-> new PoliceException("Invalid police staff id passed"));
		
		PoliceStation policeStation = stationRepo.findById(policeStationId).orElseThrow(()-> new PoliceStationException("Invalid police station id passed"));
		
		fir.setOpen(true);
		fir.setStatus(Status.valueOf("Open"));
		fir.setFiledTime(LocalDateTime.now());
		fir.setOfficerFiledFIR(police);
		fir.setPoliceStation(policeStation);
		
		police.getFirsFiled().add(fir);
		policeStation.getFirs().add(fir);
		
		customer.getFirs().add(fir);
		
		return firRepo.save(fir);
		
	}

	@Override
	public String withdrawFIR(Integer firId) throws LoginException, UserException, FIRException {
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = customerRepo.findByPhone(phone).get();
		
		FIR fir = null;
		
		List<FIR> firs = customer.getFirs();
		for(FIR f : firs) {
			if(f.getFirId() == firId) {
				fir = f;
				break;
			}
		}
		
		if(fir == null) {
			throw new FIRException("No FIR found with id : " + firId);
		}
		
		if(LocalDateTime.now().isAfter(fir.getFiledTime().plusHours(24))){
			throw new FIRException("Time limit over to withraw FIR");
		}
		
		if(!fir.isOpen()) {
			throw new FIRException("FIR is already closed");
		}
		
		fir.setOpen(false);
		fir.setStatus(Status.valueOf("Withdrawn"));
		fir.setClosedTime(LocalDateTime.now());
		
		firRepo.save(fir);
		
		return "FIR withrawn successfully";
		
	}
	
}
