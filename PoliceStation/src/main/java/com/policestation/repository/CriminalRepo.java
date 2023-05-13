package com.policestation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.policestation.model.Criminal;

public interface CriminalRepo extends JpaRepository<Criminal, Integer>{

}
