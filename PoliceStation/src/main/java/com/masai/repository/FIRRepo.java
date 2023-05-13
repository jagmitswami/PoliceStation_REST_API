package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.model.FIR;

public interface FIRRepo extends JpaRepository<FIR, Integer>{

	public FIR findByApplicantIdAndFirId(Integer applicantId, Integer firId);
	
	public FIR findByPoliceStationIdAndFirId(Integer policeStationId, Integer firId);
}
