package com.policestation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.policestation.model.Police;

public interface PoliceRepo extends JpaRepository<Police, Integer>{

	Optional<Police> findByPhone(String phone);

}
