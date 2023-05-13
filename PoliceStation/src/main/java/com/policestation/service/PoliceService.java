package com.policestation.service;

import com.policestation.exception.FIRException;
import com.policestation.exception.PoliceException;
import com.policestation.exception.PoliceStationException;
import com.policestation.exception.UnauthorizedException;
import com.policestation.exception.UserException;
import com.policestation.model.Customer;
import com.policestation.model.PoliceStation;

public interface PoliceService {

	public Customer registerPolice(Integer verficationId, Customer customer) throws UserException, UnauthorizedException;
	
	public Customer closedMaxCases() throws UserException;
	
	public String assignPoliceStationToPolice(Integer policeId, Integer policeStationId) throws PoliceException, PoliceStationException;
	
	public String changePoliceStationOfPolice(Integer policeId, Integer policeStationId) throws PoliceException, PoliceStationException;
	
	public String closeCase(Integer firId) throws FIRException;

	public PoliceStation registerPoliceStation(Integer shoId, PoliceStation policeStation) throws PoliceException;
	
}
