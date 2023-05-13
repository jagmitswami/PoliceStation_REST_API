package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.model.Criminal;

public interface CriminalRepo extends JpaRepository<Criminal, Integer>{

}
