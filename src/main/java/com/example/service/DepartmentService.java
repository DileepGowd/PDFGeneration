package com.example.service;

import java.util.List;

import com.example.model.Department;

public interface DepartmentService {

	public Department getDepartment(String name);
	
	public boolean saveDepartment(Department dept);
	
	public List<Department> getDepartments();
}
