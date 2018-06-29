package com.prongbang.sjt.dao;
 

import com.prongbang.sjt.entities.Employee;

public interface EmployeeDao extends AbstractDao<Employee, Integer>{ 
	
	public Employee findLastPk();
	
}
