package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.model.Police;

public interface PoliceRepo extends JpaRepository<Police, Integer>{

}