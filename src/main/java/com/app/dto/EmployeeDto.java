package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
	
	private String id;
	
	private Integer empId;
	
	private String empName;
	
	private String empUserName;
	
	private String empEmail;
	
	private Long empMobile;
		
	private String empAddress;
	
}
