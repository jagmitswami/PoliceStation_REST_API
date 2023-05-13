package com.policestation.service;

import com.policestation.exception.FIRException;
import com.policestation.exception.LoginException;
import com.policestation.exception.PoliceException;
import com.policestation.exception.PoliceStationException;
import com.policestation.exception.UserException;
import com.policestation.model.Customer;
import com.policestation.model.FIR;

public interface CustomerService {

	public Customer registerUser(Customer customer) throws UserException;
	
	public FIR fileFIR(FIR fir, Integer policeOfficerId, Integer policeStationId) throws LoginException, UserException, PoliceException, PoliceStationException;
	
	public String withdrawFIR(Integer firId) throws LoginException, UserException, FIRException;
	
}
