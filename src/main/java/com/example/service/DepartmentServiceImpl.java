package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Department;
import com.example.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository deptRepo;
	
	@Override
	public Department getDepartment(String name) {
		return deptRepo.findByName(name);
	}
	
	@Override
	public boolean saveDepartment(Department dept) {
		if(dept != null) {
			deptRepo.save(dept);
			return true;
		}
		return false;
	}

	@Override
	public List<Department> getDepartments() {
		List<Department> deptList = new ArrayList<>();
		deptRepo.findAll().forEach(l -> deptList.add(l));
		return deptList;
	}
	
}
