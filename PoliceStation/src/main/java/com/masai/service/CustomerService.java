package com.masai.service;

import com.masai.exception.FIRException;
import com.masai.exception.LoginException;
import com.masai.exception.PoliceException;
import com.masai.exception.PoliceStationException;
import com.masai.exception.UserException;
import com.masai.model.Customer;
import com.masai.model.FIR;

public interface CustomerService {

	public Customer registerUser(Customer customer) throws UserException;
	
	public FIR fileFIR(FIR fir, Integer policeOfficerId, Integer policeStationId) throws LoginException, UserException, PoliceException, PoliceStationException;
	
	public String withdrawFIR(Integer firId) throws LoginException, UserException, FIRException;
	
}
