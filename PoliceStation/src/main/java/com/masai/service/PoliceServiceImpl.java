package com.masai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.masai.exception.FIRException;
import com.masai.exception.LoginException;
import com.masai.exception.PoliceException;
import com.masai.exception.UnauthorizedException;
import com.masai.exception.UserException;
import com.masai.model.Customer;
import com.masai.model.FIR;
import com.masai.model.Police;
import com.masai.model.PoliceStation;
import com.masai.repository.CustomerRepo;
import com.masai.repository.FIRRepo;
import com.masai.repository.PoliceRepo;
import com.masai.repository.PoliceStationRepo;

public class PoliceServiceImpl implements PoliceService {
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	PoliceRepo policeRepo;
	
	@Autowired
	PoliceStationRepo stationRepo;
	
	@Autowired
	FIRRepo firRepo;
	
	@Override
	public Police registerPolice(Integer verficationId, Police police) throws UserException {
		
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Police existingPolice = policeRepo.findByPhone(phone).get();
		
		if(existingPolice != null) throw new UserException("User is already registered with this mobile number");
		
		police.setRole("ROLE_POLICE");
		
		police = policeRepo.save(police);
		
		return police;
	}

	@Override
	public Police closedMaxCases() throws FIRException, UserException, LoginException, UnauthorizedException {
		
		Session session = sessionRepo.findByUuid(key);
		if(session == null) throw new LoginException("Invalid User key");
		
		Police police = policeRepo.findById(session.getUserId()).orElseThrow(() -> new UserException("User not found"));

		Integer psId = police.getPoliceStationId();
		
		return null;
		
	}

	@Override
	public FIR getOldestFIR() throws FIRException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deletePolice(Integer policeId) throws PoliceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String closeCase(Integer firId) throws PoliceException, UnauthorizedException, LoginException, UserException {
		Session session = sessionRepo.findByUuid(key);
		if(session == null) throw new LoginException("Invalid User key");
		
		Customer customer = customerRepo.findById(session.getUserId()).orElseThrow(() -> new UserException("User not found"));

		if(!customer.getRole().equals("ROLE_POLICE")) {
			throw new UnauthorizedException("Unauthorized request");
		}
		
		Police police = policeRepo.findById(customer.getUserId()).get();
		
		FIR fir = firRepo.findByPoliceStationIdAndFirId(police.getPoliceStationId(), firId);
		
		fir.setOpen(false);
		fir.setOfficerClose(police.getUserId());
		
		return "FIR closed";
	}

	@Override
	public PoliceStation registerPoliceStation(int i, PoliceStation policeStation) {
		return stationRepo.save(policeStation);
	}

}
