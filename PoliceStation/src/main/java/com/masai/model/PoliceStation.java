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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PoliceStation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(access = Access.READ_ONLY)
	Integer stationId;
	
	@JsonProperty(access = Access.READ_ONLY)
	String stationCode;
	
	String address;
	
	@OneToOne
	Police officerInCharge;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "policeStation")
	List<Police> policeStationStaff;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "policeStation")
	List<FIR> firs;
	
}
