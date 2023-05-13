package com.masai.service;

import com.masai.exception.FIRException;
import com.masai.exception.LoginException;
import com.masai.exception.PoliceException;
import com.masai.exception.PoliceStationException;
import com.masai.exception.UnauthorizedException;
import com.masai.exception.UserException;
import com.masai.model.Police;
import com.masai.model.PoliceStation;

public interface PoliceService {

	public Police registerPolice(Integer verficationId, Police police) throws UserException;
	
	public Police closedMaxCases() throws FIRException, UserException, LoginException, UnauthorizedException;
	
	public String assignPoliceStationToPolice(Integer policeId, Integer policeStationId) throws PoliceException, PoliceStationException;
	
	public String changePoliceStationOfPolice(Integer policeId, Integer policeStationId) throws PoliceException, PoliceStationException;
	
	public String closeCase(Integer firId) throws PoliceException, UnauthorizedException, LoginException, UserException, FIRException;

	public PoliceStation registerPoliceStation(Integer shoId, PoliceStation policeStation) throws PoliceException;
	
}
