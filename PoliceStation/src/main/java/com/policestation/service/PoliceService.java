package com.policestation.service;

import com.policestation.exception.FIRException;
import com.policestation.exception.LoginException;
import com.policestation.exception.PoliceException;
import com.policestation.exception.PoliceStationException;
import com.policestation.exception.UnauthorizedException;
import com.policestation.exception.UserException;
import com.policestation.model.Police;
import com.policestation.model.PoliceStation;

public interface PoliceService {

	public Police registerPolice(Integer verficationId, Police police) throws UserException;
	
	public Police closedMaxCases() throws FIRException, UserException, LoginException, UnauthorizedException;
	
	public String assignPoliceStationToPolice(Integer policeId, Integer policeStationId) throws PoliceException, PoliceStationException;
	
	public String changePoliceStationOfPolice(Integer policeId, Integer policeStationId) throws PoliceException, PoliceStationException;
	
	public String closeCase(Integer firId) throws PoliceException, UnauthorizedException, LoginException, UserException, FIRException;

	public PoliceStation registerPoliceStation(Integer shoId, PoliceStation policeStation) throws PoliceException;
	
}
