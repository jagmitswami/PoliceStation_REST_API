package com.policestation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.policestation.model.PoliceStation;

public interface PoliceStationRepo extends JpaRepository<PoliceStation, Integer>{

}
