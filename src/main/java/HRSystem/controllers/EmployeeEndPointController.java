package HRSystem.controllers;

import HRSystem.model.Employee;
import HRSystem.utils.EntryDataValidator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hrsystem")

public class EmployeeEndPointController {

    //Initialize of instances
    private List<Employee> listOfEmployees = new ArrayList<>();
    private final EntryDataValidator validator = new EntryDataValidator();

    /**
     * Writes out all the employees in the list
     * @return list of all employees
     */
    @GetMapping
    public List<Employee> getAllEmployees() {
        return listOfEmployees;
    }

    /**
     * Add new employee to the list with all parameters
     * @param employee
     * @return Confirmation about successful creation.
     */
    @PostMapping
    public String createEmployee(@RequestBody Employee employee) {
        // Check if any required parameter is null or empty
        if (validator.isAnyParameterNullOrEmpty(employee.getEmployeeName(),
                employee.getEmployeeSurname(),
                employee.getEmployeeAge(),
                employee.getEmployeePosition())) {
            return "All parameters are required for creating an employee.";
        }

        Long maxId = 0L; // Initialize maxId with 0
        // Loop through each employee in the list to find the maximum ID
        for (Employee e : listOfEmployees) {
            if (e.getEmployeeId() > maxId) {
                maxId = e.getEmployeeId(); // Update maxId if a larger ID is found
            }
        }

        // Increase maximum ID by 1 to generate a new ID for the new employee
        Long newId = maxId + 1;
        // Set the new ID for the employee and add it to the list
        employee.setEmployeeId(newId);
        listOfEmployees.add(employee);

        // Return a message indicating that the employee was successfully created with information about assigned ID.
        return "Employee was successfully added. Employee ID: " + newId;
    }

    /**
     * Update employee in the list according selected employeeID, any parameter can be changed except ID
     * @param employeeId ID of employee
     * @param updatedEmployee employee with updated parameters
     * @return Confirmation about successful update.
     */
    @PutMapping("/{employeeId}")
    public String updateEmployeeDetails(@PathVariable Long employeeId, @RequestBody Employee updatedEmployee) {
        // Check if any required parameter is null or empty
        if (validator.isAnyParameterNullOrEmpty(updatedEmployee.getEmployeeName(),
                updatedEmployee.getEmployeeSurname(),
                updatedEmployee.getEmployeeAge(),
                updatedEmployee.getEmployeePosition())) {
            return "All parameters are required for updating an employee.";
        }

        for (Employee employee : listOfEmployees) {
            if (employee.getEmployeeId().equals(employeeId)) {
                // Update the employee details
                employee.setEmployeeName(updatedEmployee.getEmployeeName());
                employee.setEmployeeSurname(updatedEmployee.getEmployeeSurname());
                employee.setEmployeeAge(updatedEmployee.getEmployeeAge());
                employee.setEmployeePosition(updatedEmployee.getEmployeePosition());
                // Return a message indicating that the employee was successfully updated
                return "Employee with ID " + employeeId + " was updated.";
            }
        }
        return "Employee with ID " + employeeId + " not found.";
    }

    /**
     * Remove employee from the list according selected employeeID
     * @param employeeId ID of employee
     * @return Confirmation about successful erasing.
     */
    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId) {
        for (Employee employee : listOfEmployees) {
            if (employee.getEmployeeId().equals(employeeId)) {
                listOfEmployees.remove(employee);
                return "Employee with ID " + employeeId + " was removed.";
            }
        }
        return "Employee with ID " + employeeId + " not found.";
    }

}
