package com.policestation.model;


import java.util.ArrayList;
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
	private Integer policeId;
	private String firstName;   
	private String lastName;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String Phone;
	private String address;
	private Integer age;
	private String gender;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@JsonIgnore
	private String role;
	
	private String department;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "officerFiledFIR")
	private List<FIR> firsFiled = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "officerClosedFIR")
	private List<FIR> firsClosed = new ArrayList<>();
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	private PoliceStation policeStation;
	
}
