package com.example.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.model.Department;
import com.example.model.Employee;
import com.example.pdfview.PDFGenerator;
import com.example.service.EmployeeServiceImpl;
import com.example.service.DepartmentService;

/**
 * Employee Controller for perform CRUD operations
 * 
 * @author dileep
 *
 */
@Controller
public class EmpController {

	@Autowired
	private EmployeeServiceImpl empService;

	@Autowired
	private DepartmentService deptService;

	/**
	 * Used to save the Employee
	 * 
	 * @param employee
	 * @return HttpStatus
	 */
	@RequestMapping(path = "/employee", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
	public ResponseEntity<Void> insertEmployee(@RequestBody Employee employee) {
		if (employee != null) {
			Department exitsDept = deptService.getDepartment(employee.getDepartment().getName());
			// If department exits it saves only employee. Else it saves both.
			if (employee.getDepartment().equals(exitsDept)) {
				Employee emp = empService.getEmployeeByName(employee.getName());
				// If employee exits updates employee
				if (emp != null && employee.getName().equals(emp.getName())) {
					emp.setSalary(employee.getSalary());
					empService.insertEmployee(emp);
					return new ResponseEntity<Void>(HttpStatus.FOUND);
				}
				empService.insertEmployee(employee);
				return new ResponseEntity<Void>(HttpStatus.CREATED);
			} else {
				bothInsertion(employee);
				return new ResponseEntity<Void>(HttpStatus.CREATED);
			}

		}
		return new ResponseEntity<Void>(HttpStatus.GATEWAY_TIMEOUT);
	}

	/**
	 * To get employee with id or name with request parameter.
	 * 
	 * @param id(optional)
	 * @param name(optional)
	 * @return Employee Object
	 */
	@RequestMapping(path = "/employee", method = RequestMethod.GET)
	public ResponseEntity<Employee> getEmployee(@RequestParam Optional<Integer> id,
			@RequestParam Optional<String> name) {
		Employee emp = null;
		if (id.isPresent()) {
			emp = empService.getEmployee(id.get());
		} else if (name.isPresent()) {
			emp = empService.getEmployeeByName(name.get());
		}
		return new ResponseEntity<Employee>(emp, HttpStatus.OK);
	}

	/**
	 * 
	 * @return PDF of Employees
	 */
	@RequestMapping(value = "/downloadPDF", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> downloadExcel() {
		// create some sample data
		List<Employee> employees = empService.getEmployees();
		ByteArrayInputStream bais = PDFGenerator.customerPDFReport(employees);
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=employees.pdf");
 
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bais));
	}

	/**
	 * Returns all Employees
	 * 
	 * @return List of Employees
	 */
	@RequestMapping(path = "/employees", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> list = empService.getEmployees().stream()
				.sorted((k1, k2) -> k1.getName().compareTo(k2.getName())).collect(Collectors.toList());
		return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
	}

	@GetMapping("/empList/{field}/{type}")
	public ResponseEntity<List<Employee>> getEmps(@PathVariable("type") String type,
			@PathVariable("field") String filed) {
		List<Employee> list = empService.getWithSort(type, filed);
		return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
	}

	// To save both Employee and Department.
	private void bothInsertion(Employee employee) {
		Department dept = new Department();
		dept.setId(employee.getDepartment().getId());
		dept.setName(employee.getDepartment().getName());
		Employee eNew = new Employee();
		eNew.setName(employee.getName());
		eNew.setSalary(employee.getSalary());
		eNew.setDepartment(dept);
		System.out.println(eNew);
		deptService.saveDepartment(dept);
		empService.insertEmployee(eNew);
	}
}
