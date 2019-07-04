package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.model.Department;

public interface DepartmentRepository extends CrudRepository<Department, Integer>{

	Department findByName(String name);
	
}
