package hrsystem.repositories;

import hrsystem.model.Employee;
import hrsystem.utils.EntryDataValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    //Initialize instances
    private EntryDataValidator validator = new EntryDataValidator();
    private String jdbcURL = "jdbc:sqlite:/C:/Users/User/Desktop/SQlite/DB_Browser_for_SQLite/hrsystem.db";

    /**
     * Writes out all the employees from database
     *
     * @return list of all employees
     */
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
            System.err.println("Error retrieving employees from the database: ");
            e.printStackTrace();
        }
        return employeesFromDatabase;
    }

    /**
     * Add new employee to the database with all parameters
     *
     * @param employee
     * @return Confirmation about successful creation
     */
    public String createEmployee(Employee employee) {

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
            return "Employee was successfully added to the database.";
        } catch (SQLException e) {
            System.err.println("Error inserting employee into the database: ");
            e.printStackTrace();
            return "Failed to add employee to the database.";
        }
    }

    /**
     * Update employee in the database according selected employee ID, any parameter can be changed except ID
     *
     * @param employeeId ID of employee
     * @param updatedEmployee employee with updated parameters
     * @return Confirmation about successful update.
     */
    public String updateEmployeeDetails(Long employeeId, Employee updatedEmployee) {

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
            System.err.println("Error updating employee in the database: ");
            e.printStackTrace();
            return "Failed to update employee.";
        }
    }

    /**
     * Remove employee from the database according selected employee ID.
     *
     * @param employeeId ID of employee
     * @return Confirmation about successful erasing
     */
    public String deleteEmployee(Long employeeId) {
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
            System.err.println("Error deleting employee from the database: ");
            e.printStackTrace();
            return "Failed to delete employee.";
        }
    }

    /**
     * Count employees on each position and count average of their age
     * @return list with result position, sum of employees, average age
     */
    public List<String> getEmployeeStatistic() {
        List<String> statistics = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(jdbcURL)) {
            String sql = "SELECT employeePosition AS Position, COUNT(*) AS Count_of_employees, AVG(employeeAge) AS Average_Age FROM AllEmployees GROUP BY employeePosition ORDER BY Count_of_employees DESC;";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    String position = resultSet.getString("Position");
                    int employeeCount = resultSet.getInt("Count_of_employees");
                    double averageAge = resultSet.getDouble("Average_Age");
                    String row = position + ", " + employeeCount + ", " + averageAge;
                    statistics.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statistics;
    }
}