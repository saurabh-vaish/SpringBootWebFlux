package com.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "EmployeeFlux")
public class Employee {
	
	@Id
	private String id;
	
	private Integer empId;
	
	private String empName;
	
	private String empUserName;
	
	private String empEmail;
	
	private Long empMobile;
		
	private String empAddress;
	
}
