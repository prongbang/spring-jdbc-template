package com.prongbang.sjt.service;

import com.prongbang.sjt.entities.Employee;

public interface EmployeeService extends AbstractService<Employee, Integer> {

	public Employee findLastPk();

}
