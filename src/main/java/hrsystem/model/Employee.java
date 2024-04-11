package hrsystem.model;

public class Employee {
        /**
         * ID of employee. This parameter is automatically generated when Employee is created.
         */
        private Long employeeId;

        /**
         * Name of employee
         */
        private String employeeName;

        /**
         * Surname of employee
         */
        private String employeeSurname;

        /**
         * Age of employee
         */
        private String employeeAge;

        /**
         * Position of employee
         */
        private String employeePosition;

        /**
         * Constructor without parameters
         */
        public Employee() {
        }

        /**
         * Constructor with parameters, creates new employee with all necessary parameters
         * @param employeeId - ID of employee
         * @param employeeName - Name of employee
         * @param employeeSurname - Surname of employee
         * @param employeeAge - Age of employee
         */
        public Employee (Long employeeId, String employeeName, String employeeSurname, String employeeAge, String employeePosition) {
            this.employeeId = employeeId;
            this.employeeName = employeeName;
            this.employeeSurname = employeeSurname;
            this.employeeAge = employeeAge;
            this.employeePosition = employeePosition;
        }

        /**
         * Getter, returns ID of employee
         * @return ID
         */
        public Long getEmployeeId() {
            return employeeId;
        }

        /**
         * Setter, set new ID of employee
         * @param employeeId - ID
         */
        public void setEmployeeId(Long employeeId) {
            this.employeeId = employeeId;
        }

        /**
         * Getter, returns name of employee
         * @return name
         */
        public String getEmployeeName() {
            return employeeName;
        }

        /**
         * Setter, set new name of employee
         * @param employeeName - name
         */
        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        /**
         * Getter, returns surname of employee
         * @return surname
         */
        public String getEmployeeSurname() {
            return employeeSurname;
        }

        /**
         * Setter, set new surname of employee
         * @param employeeSurname - surname
         */
        public void setEmployeeSurname(String employeeSurname) {
            this.employeeSurname = employeeSurname;
        }

        /**
         * Getter, returns age of employee
         * @return employee age
         */
        public String getEmployeeAge() {
            return employeeAge;
        }

        /**
         * Setter, set age of employee
         * @param employeeAge - employee age
         */
        public void setEmployeeAge(String employeeAge) {
            this.employeeAge = employeeAge;
        }

    /**
     * Getter, returns position of employee
     * @return employee position
     */
        public String getEmployeePosition() {
            return employeePosition;
    }

    /**
     * Setter, set age of employee
     * @param employeePosition - employee position
     */
        public void setEmployeePosition(String employeePosition) {
            this.employeePosition = employeePosition;
    }
}

