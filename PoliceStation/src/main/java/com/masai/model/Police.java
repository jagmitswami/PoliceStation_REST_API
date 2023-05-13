package com.masai.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Police{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(access = Access.READ_ONLY)
	Integer policeId;
	String firstName;   
	String lastName;
	String Phone;
	String address;
	Integer age;
	String gender;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	String password;
	
	@JsonIgnore
	String role;
	
	String department;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "officerFiledFIR")
	List<FIR> firsFiled;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "officerClosedFIR")
	List<FIR> firsClosed;
	
	@JsonIgnore
	@ManyToOne
	PoliceStation policeStation;
	
}
