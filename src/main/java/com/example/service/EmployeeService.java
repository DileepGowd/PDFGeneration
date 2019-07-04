package com.example.service;

import java.util.List;

import com.example.model.Employee;

public interface EmployeeService {

	public boolean insertEmployee(Employee emp);
	
	public Employee getEmployee(int id);
	
	public Employee getEmployeeByName(String name);
	
	public List<Employee> getEmployees();
	
}
