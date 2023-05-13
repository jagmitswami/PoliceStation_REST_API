package com.masai.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FIR {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(access = Access.READ_ONLY)
	private Integer firId;
	
	@JsonProperty(access = Access.READ_ONLY)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:sss")
	private LocalDateTime timeStamp;
	
	@JsonIgnore
	@ManyToOne
	private Customer customer;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Criminal> criminals;
	
	@JsonProperty(access = Access.READ_ONLY)
	@ManyToOne
	private Police officerFiledFIR;

	@JsonProperty(access = Access.READ_ONLY)
	@ManyToOne
	private Police officerClosedFIR;
	
	@ManyToOne
	private PoliceStation policeStation;
	
	private boolean isOpen; 
	
}
