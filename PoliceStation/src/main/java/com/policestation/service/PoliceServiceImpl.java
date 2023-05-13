package com.policestation.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.policestation.exception.FIRException;
import com.policestation.exception.PoliceException;
import com.policestation.exception.PoliceStationException;
import com.policestation.exception.UnauthorizedException;
import com.policestation.exception.UserException;
import com.policestation.model.Customer;
import com.policestation.model.FIR;
import com.policestation.model.PoliceStation;
import com.policestation.model.Status;
import com.policestation.repository.CustomerRepo;
import com.policestation.repository.FIRRepo;
import com.policestation.repository.PoliceStationRepo;

public class PoliceServiceImpl implements PoliceService {
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	PoliceStationRepo stationRepo;
	
	@Autowired
	FIRRepo firRepo;
	
	//any
	@Override
	public Customer registerPolice(Integer verficationId, Customer customer) throws UserException, UnauthorizedException {
		
		if(verficationId != 8080) {
			throw new UnauthorizedException("Unauthorized request");
		}
		
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer existingCustomer = customerRepo.findByPhone(phone).get();
		
		if(existingCustomer != null && existingCustomer.getRole().equals("ROLE_POLICE")) {
			throw new UserException("Your account is already registered");
		}
		
		customer.setRole("ROLE_POLICE");
		
		return customerRepo.save(customer);
		
	}

	//sho
	@Override
	public Customer closedMaxCases() throws UserException{
		
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer sho = customerRepo.findByPhone(phone).get();
		
		PoliceStation policeStation = sho.getPoliceStation();
		
		List<Customer> polices = policeStation.getPoliceStationStaff();
		int max = 0;
		Customer police = null;
		
		for(Customer p : polices) {
			if(max < p.getFirsClosed().size()) {
				police = p;
				max = p.getFirsClosed().size();
			}
		}
		
		if(police == null) {
			throw new UserException("No record found");
		}
		
		return police;
		
	}
	
	//admin
		@Override
		public PoliceStation registerPoliceStation(Integer shoId, PoliceStation policeStation) throws PoliceException {
			
			Optional<Customer> opt = customerRepo.findById(shoId);
			if(opt.isEmpty()) {
				throw new PoliceException("No police staff found with id : " + shoId);
			}
			Customer sho = opt.get();
			
			policeStation.setOfficerInCharge(sho);
			
			return stationRepo.save(policeStation);
			
		}

	//admin
	@Override
	public String assignPoliceStationToPolice(Integer policeId, Integer policeStationId) throws PoliceException, PoliceStationException {
		Optional<Customer> optPolice = customerRepo.findById(policeId);

		if(optPolice.isEmpty()) {
			throw new PoliceException("No Police staff found with id : "+ policeStationId);
		}
		
		Optional<PoliceStation> optStation = stationRepo.findById(policeStationId);

		if(optStation.isEmpty()) {
			throw new PoliceStationException("No Police station found with id : "+ policeStationId);
		}
		
		Customer police = optPolice.get();
		
		if(!police.getRole().equals("ROLE_POLICE")) {
			throw new PoliceException("No Police staff found with id : "+ policeStationId);
		}
		
		PoliceStation policeStation = optStation.get();
		
		police.setPoliceStation(policeStation);
		policeStation.getPoliceStationStaff().add(police);
		
		stationRepo.save(policeStation);
		
		return "Assigned police station successfully";
		
	}

	//admin
	@Override
	public String changePoliceStationOfPolice(Integer policeId, Integer policeStationId) throws PoliceException, PoliceStationException {
		Optional<Customer> optPolice = customerRepo.findById(policeId);

		if(optPolice.isEmpty()) {
			throw new PoliceException("No Police staff found with id : "+ policeStationId);
		}
		
		Optional<PoliceStation> optStation = stationRepo.findById(policeStationId);

		if(optStation.isEmpty()) {
			throw new PoliceStationException("No Police station found with id : "+ policeStationId);
		}
		
		Customer police = optPolice.get();
		
		if(!police.getRole().equals("ROLE_POLICE")) {
			throw new PoliceException("No Police staff found with id : "+ policeStationId);
		}
		
		PoliceStation policeStation = optStation.get();
		
		police.setPoliceStation(policeStation);
		policeStation.getPoliceStationStaff().add(police);
		
		stationRepo.save(policeStation);
		
		return "Changed police station successfully";
		
	}

	//police
	@Override
	public String closeCase(Integer firId) throws FIRException {
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer police = customerRepo.findByPhone(phone).get();
		
		FIR fir = null;
		
		List<FIR> firs = police.getPoliceStation().getFirs();
		for(FIR f: firs) {
			if(f.getFirId() == firId) {
				fir = f;
				break;
			}
		}
		
		if(fir == null) {
			throw new FIRException("No FIR found with id : " + firId);
		}
		
		if(!fir.isOpen()) {
			throw new FIRException("FIR is already closed");
		}
		
		fir.setOpen(false);
		fir.setStatus(Status.valueOf("Closed"));
		fir.setClosedTime(LocalDateTime.now());
		fir.setOfficerClosedFIR(police);
		
		firRepo.save(fir);
		
		return "FIR closed successfully";
		
	}

}
