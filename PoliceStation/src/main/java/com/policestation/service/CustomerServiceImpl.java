package com.policestation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.policestation.exception.FIRException;
import com.policestation.exception.PoliceException;
import com.policestation.exception.PoliceStationException;
import com.policestation.exception.UserException;
import com.policestation.model.Customer;
import com.policestation.model.FIR;
import com.policestation.model.PoliceStation;
import com.policestation.model.Status;
import com.policestation.repository.CustomerRepo;
import com.policestation.repository.FIRRepo;
import com.policestation.repository.PoliceStationRepo;

public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	CustomerRepo customerRepo;
	
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
	public FIR fileFIR(FIR fir, Integer policeOfficerId, Integer policeStationId) throws PoliceException, PoliceStationException {
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = customerRepo.findByPhone(phone).get();
		
		Customer police = customerRepo.findById(policeOfficerId).orElseThrow(()-> new PoliceException("Invalid police staff id passed"));
		if(police.getRole().equals("ROLE_CUSTOMER")) {
			new PoliceException("Invalid police staff id passed");
		}
		
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
	public String withdrawFIR(Integer firId) throws FIRException {
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
