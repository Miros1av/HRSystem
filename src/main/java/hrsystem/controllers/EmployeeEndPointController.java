package hrsystem.controllers;

import hrsystem.model.Employee;
import hrsystem.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hrsystem")
public class EmployeeEndPointController {
    private final EmployeeRepository employeeRepository;

    public EmployeeEndPointController() {
        this.employeeRepository = new EmployeeRepository();
    }

    //Endpoint for getting of the list of all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    //Endpoint for adding of new employee to the database
    @PostMapping
    public String createEmployee(@RequestBody Employee employee) {
        return employeeRepository.createEmployee(employee);
    }

    //Endpoint for update of new employee in the database
    @PutMapping("/{employeeId}")
    public String updateEmployeeDetails(@PathVariable Long employeeId, @RequestBody Employee updatedEmployee) {
        return employeeRepository.updateEmployeeDetails(employeeId, updatedEmployee);
    }

    //Endpoint for deleting of an employee from the database
    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId) {
        return employeeRepository.deleteEmployee(employeeId);
    }

    //Endpoint for obtaining statistic about employees from the database
    @GetMapping("/statistic")
    public List<String> getEmployeeStatistic() {
        return employeeRepository.getEmployeeStatistic();
    }
}