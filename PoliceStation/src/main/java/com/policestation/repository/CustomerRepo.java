package com.policestation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.policestation.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{

	public Optional<Customer> findByPhone(String mobileNumber);

}
