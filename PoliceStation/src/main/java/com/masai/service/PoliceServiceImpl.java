package com.masai.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.masai.exception.FIRException;
import com.masai.exception.LoginException;
import com.masai.exception.PoliceException;
import com.masai.exception.PoliceStationException;
import com.masai.exception.UnauthorizedException;
import com.masai.exception.UserException;
import com.masai.model.FIR;
import com.masai.model.Police;
import com.masai.model.PoliceStation;
import com.masai.model.Status;
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
	
	//any
	@Override
	public Police registerPolice(Integer verficationId, Police police) throws UserException {
		
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Police existingPolice = policeRepo.findByPhone(phone).get();
		
		if(existingPolice != null) throw new UserException("User is already registered with this mobile number");
		
		police.setRole("ROLE_POLICE");
		
		police = policeRepo.save(police);
		
		return police;
		
	}

	//sho
	@Override
	public Police closedMaxCases() throws FIRException, UserException, LoginException, UnauthorizedException {
		
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Police sho = policeRepo.findByPhone(phone).get();
		
		PoliceStation policeStation = sho.getPoliceStation();
		
		List<Police> polices = policeStation.getPoliceStationStaff();
		int max = 0;
		Police police = null;
		
		for(Police p : polices) {
			if(max < p.getFirsClosed().size()) {
				police = p;
				max = p.getFirsClosed().size();
			}
		}
		
		return police;
		
	}
	
	//admin
		@Override
		public PoliceStation registerPoliceStation(Integer shoId, PoliceStation policeStation) throws PoliceException {
			
			Optional<Police> opt = policeRepo.findById(shoId);
			if(opt.isEmpty()) {
				throw new PoliceException("No police staff found with id : " + shoId);
			}
			Police sho = opt.get();
			
			policeStation.setOfficerInCharge(sho);
			
			return stationRepo.save(policeStation);
			
		}

	//admin
	@Override
	public String assignPoliceStationToPolice(Integer policeId, Integer policeStationId) throws PoliceException, PoliceStationException {
		Optional<Police> optPolice = policeRepo.findById(policeId);

		if(optPolice.isEmpty()) {
			throw new PoliceException("No Police staff found with id : "+ policeStationId);
		}
		
		Optional<PoliceStation> optStation = stationRepo.findById(policeStationId);

		if(optStation.isEmpty()) {
			throw new PoliceStationException("No Police station found with id : "+ policeStationId);
		}
		
		Police police = optPolice.get();
		PoliceStation policeStation = optStation.get();
		
		police.setPoliceStation(policeStation);
		policeStation.getPoliceStationStaff().add(police);
		
		stationRepo.save(policeStation);
		
		return "Assigned police station successfully";
		
	}

	//admin
	@Override
	public String changePoliceStationOfPolice(Integer policeId, Integer policeStationId) throws PoliceException, PoliceStationException {
		Optional<Police> optPolice = policeRepo.findById(policeId);

		if(optPolice.isEmpty()) {
			throw new PoliceException("No Police staff found with id : "+ policeStationId);
		}
		
		Optional<PoliceStation> optStation = stationRepo.findById(policeStationId);

		if(optStation.isEmpty()) {
			throw new PoliceStationException("No Police station found with id : "+ policeStationId);
		}
		
		Police police = optPolice.get();
		PoliceStation policeStation = optStation.get();
		
		police.setPoliceStation(policeStation);
		policeStation.getPoliceStationStaff().add(police);
		
		stationRepo.save(policeStation);
		
		return "Changed police station successfully";
		
	}

	//police
	@Override
	public String closeCase(Integer firId) throws PoliceException, UnauthorizedException, LoginException, UserException, FIRException {
		String phone = SecurityContextHolder.getContext().getAuthentication().getName();
		Police police = policeRepo.findByPhone(phone).get();
		
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
