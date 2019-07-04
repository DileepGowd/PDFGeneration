package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository empRepo;
	
	@Override
	public boolean insertEmployee(Employee emp) {
		if(emp != null) {
			empRepo.save(emp);
			return true;
		}
		return false;
	}
	
	@Override
	public Employee getEmployee(int id) {
		return empRepo.getById(id);
	}
	
	@Override
	public Employee getEmployeeByName(String name) {
		return empRepo.findByName(name);
	}

	@Override
	public List<Employee> getEmployees() {
		List<Employee> empList = new ArrayList<>();
		empRepo.findAll().forEach(l -> empList.add(l));
		return empList;
	}
	
	public List<Employee> getWithSort(String type, String sort_param){
		return empRepo.findAll(Sort.by(Sort.Direction.fromString(type), sort_param));
	}
}
