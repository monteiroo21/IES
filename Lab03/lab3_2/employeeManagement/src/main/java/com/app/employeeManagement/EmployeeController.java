package com.app.employeeManagement;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    public Iterable<Employee> showEmployeeList() {
        return employeeRepository.findAll();
    }
    
    @PostMapping("/addemployee")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    Employee one(@PathVariable Long id) {
      
      return employeeRepository.findById(id)
        .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    public Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return employeeRepository.findById(id)
            .map(employee -> {
                employee.setName(newEmployee.getName());
                employee.setEmail(newEmployee.getEmail());
                return employeeRepository.save(employee);
            })
            .orElseGet(() -> {
                return employeeRepository.save(newEmployee);
            });
    }
        
    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }

    @GetMapping("/employees?email={email}")
    public Employee getEmployeeByEmail(@PathVariable String email) {
        return employeeRepository.findByEmail(email)
        .orElseThrow(() -> new EmailNotFoundException(email));
    }
}
