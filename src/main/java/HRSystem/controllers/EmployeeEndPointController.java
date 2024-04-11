package HRSystem.controllers;

import HRSystem.model.Employee;
import HRSystem.utils.EntryDataValidator;

import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hrsystem")
public class EmployeeEndPointController {

    //Initialize instances
    private EntryDataValidator validator = new EntryDataValidator();
    private String jdbcURL = "jdbc:sqlite:/C:/Users/User/Desktop/SQlite/DB_Browser_for_SQLite/HRSystem.db";

    /**
     * Writes out all the employees from database
     *
     * @return list of all employees
     */
    @GetMapping
    public List<Employee> getAllEmployees() {
        List<Employee> employeesFromDatabase = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcURL)) {
            String sql = "SELECT * FROM AllEmployees";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                Long id = result.getLong("employeeID");
                String name = result.getString("employeeName");
                String surname = result.getString("employeeSurname");
                String age = result.getString("employeeAge");
                String position = result.getString("employeePosition");
                Employee employee = new Employee(id, name, surname, age, position);
                employeesFromDatabase.add(employee);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving employees from the database: " + e.getMessage());
        }
        return employeesFromDatabase;
    }

    /**
     * Add new employee to the database with all parameters
     *
     * @param employee
     * @return Confirmation about successful creation
     */
    @PostMapping
    public String createEmployee(@RequestBody Employee employee) {
        if (validator.isAnyParameterNullOrEmpty(employee.getEmployeeName(),
                employee.getEmployeeSurname(),
                employee.getEmployeeAge(),
                employee.getEmployeePosition())) {
            return "All parameters are required for adding an employee to the database.";
        }

        try (Connection connection = DriverManager.getConnection(jdbcURL)) {
            String sql = "INSERT INTO AllEmployees (employeeName, employeeSurname, employeeAge, employeePosition) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, employee.getEmployeeName());
            statement.setString(2, employee.getEmployeeSurname());
            statement.setString(3, employee.getEmployeeAge());
            statement.setString(4, employee.getEmployeePosition());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting employee into the database: " + e.getMessage());
            return "Failed to add employee to the database.";
        }
        return "Employee was successfully added to the database.";
    }

    /**
     * Update employee in the database according selected employee ID, any parameter can be changed except ID
     *
     * @param employeeId ID of employee
     * @param updatedEmployee employee with updated parameters
     * @return Confirmation about successful update.
     */
    @PutMapping("/{employeeId}")
    public String updateEmployeeDetails(@PathVariable Long employeeId, @RequestBody Employee updatedEmployee) {
        if (validator.isAnyParameterNullOrEmpty(updatedEmployee.getEmployeeName(),
                updatedEmployee.getEmployeeSurname(),
                updatedEmployee.getEmployeeAge(),
                updatedEmployee.getEmployeePosition())) {
            return "All parameters are required for updating an employee.";
        }

        try (Connection connection = DriverManager.getConnection(jdbcURL)) {
            String sql = "UPDATE AllEmployees SET employeeName = ?, employeeSurname = ?, employeeAge = ?, employeePosition = ? WHERE employeeId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, updatedEmployee.getEmployeeName());
            statement.setString(2, updatedEmployee.getEmployeeSurname());
            statement.setString(3, updatedEmployee.getEmployeeAge());
            statement.setString(4, updatedEmployee.getEmployeePosition());
            statement.setLong(5, employeeId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return "Employee with ID " + employeeId + " was successfully updated.";
            } else {
                return "Employee with ID " + employeeId + " not found.";
            }
        } catch (SQLException e) {
            System.err.println("Error updating employee in the database: " + e.getMessage());
            return "Failed to update employee.";
        }
    }

    /**
     * Remove employee from the database according selected employee ID.
     *
     * @param employeeId ID of employee
     * @return Confirmation about successful erasing
     */
    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId) {
        try (Connection connection = DriverManager.getConnection(jdbcURL)) {
            String sql = "DELETE FROM AllEmployees WHERE employeeId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, employeeId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                return "Employee with ID " + employeeId + " was successfully removed.";
            } else {
                return "Employee with ID " + employeeId + " not found.";
            }
        } catch (SQLException e) {
            System.err.println("Error deleting employee from the database: " + e.getMessage());
            return "Failed to delete employee.";
        }
    }
}