package com.policestation.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoliceDTO{
	
	private String firstName;   
	private String lastName;
	private Integer age;
	private String gender;
	private Integer policeStationId;
	
	
}
