import java.io.*;
import java.util.*;

public class StaffService implements Service {
    private final BufferedReader reader;
    private BufferedWriter writer;
    private static HashMap<String, Employee> employees;

    public StaffService(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.writer = writer;
        employees = readData();  // Read data from database file when application starts
    }

    public static HashMap<String, Employee> getEmployees() {
        return employees;
    }

    @Override
    public void addEmployee() {
        String firstName, lastName, department, role, startDate;
        double salary;
        System.out.println("Enter employee details - First Name, Last Name, Department, Role, Salary: ");
        firstName = Validation.validateInput("First Name: ", new Validation.LettersAndSpaceValidator());
        lastName = Validation.validateInput("Last Name: ", new Validation.LettersAndSpaceValidator());
        department = Validation.validateInput("Choose department:", new Validation.DepartmentValidator());
        role = Validation.validateInput("Role: ", new Validation.LettersAndSpaceValidator());
        salary = Integer.parseInt(Validation.validateInput("Salary: ", new Validation.DigitsValidator()));
        startDate = Validation.validateInput("Start date: ", new Validation.DateValidator());
        Employee newEmployee = new Employee(firstName, lastName, department, role, salary, startDate);
        employees.put(newEmployee.getId(), newEmployee);
    }

    @Override
    public void editEmployee() {
        System.out.println("Enter the ID of the employee you want to edit: ");
        String employeeId = Validation.validateInput("ID: ", new Validation.IdValidator());

        if (employees.containsKey(employeeId)) {
            // get employee by ID
            Employee employeeToEdit = employees.get(employeeId);
            // Request new data from the user
            System.out.println("Enter new details for the employee (or press Enter to keep existing values):");
            String newFirstName = Validation.validateInput("First Name [" + employeeToEdit.getFirstName() + "]: ",
                    new Validation.LettersAndSpaceValidator(), true);
            if (!newFirstName.isEmpty()) {
                employeeToEdit.setFirstName(newFirstName);
                System.out.printf("First name changed to %s%n%n", newFirstName);
            }

            String newLastName = Validation.validateInput("Last Name [" + employeeToEdit.getLastName() + "]: ",
                    new Validation.LettersAndSpaceValidator(), true);
            if (!newLastName.isEmpty()) {
                employeeToEdit.setLastName(newLastName);
                System.out.printf("Last name changed to %s%n%n", newLastName);
            }

            String newDepartment = Validation.validateInput("Department [" + employeeToEdit.getDepartment() + "] " +
                    "Choose new department: ", new Validation.DepartmentValidator(), true);
            if (!newDepartment.isEmpty()) {
                employeeToEdit.setDepartment(newDepartment);
                System.out.printf("Department changed to %s%n%n", newDepartment);
            }

            String newRole = Validation.validateInput("Role [" + employeeToEdit.getRole() + "]: ",
                    new Validation.LettersAndSpaceValidator(), true);
            if (!newRole.isEmpty()) {
                employeeToEdit.setRole(newRole);
                System.out.printf("Role changed to %s%n%n", newRole);
            }

            String inputSalary = Validation.validateInput("Salary [" + employeeToEdit.getSalary() + "]: ",
                    new Validation.DigitsValidator(), true);
            if (!inputSalary.isEmpty()) {
                double newSalary = Double.parseDouble(inputSalary);
                employeeToEdit.setSalary(newSalary);
                System.out.printf("Salary changed to %.2f%n%n", newSalary);
            }

            String newStartDate = Validation.validateInput("Start date [" + employeeToEdit.getStartDate() + "]: ",
                    new Validation.DateValidator(), true);
            if (!newStartDate.isEmpty()) {
                employeeToEdit.setStartDate(newStartDate);
                System.out.printf("Start date changed to %s%n%n", newStartDate);
            }

            // notify user that the employee has been successfully edited
            System.out.println("Employee edited successfully.");
            System.out.println();
        } else {
            System.out.println("Employee with ID " + employeeId + " not found.");
            System.out.println();
        }
    }


    @Override
    public void fireEmployee() {
        // Enter ID of the employee you want to fire
        String employeeId = Validation.validateInput("Enter the ID of the employee you want to fire (e.g. 0001 to 9999): ",
                new Validation.IdValidator());

        // check if employee with this ID exist
        if (employees.containsKey(employeeId)) {
            // get employee by ID
            Employee employeeToFire = employees.get(employeeId);

            // display information about the employee before fire
            System.out.println("Firing the following employee:");
            displayEmployee(employeeToFire);

            // Confirm you really want to fire employee
            String confirmation = Validation.validateInput("Are you sure you want to fire this employee? (yes/no): ",
                    new Validation.LettersAndSpaceValidator());

            if (confirmation.equals("Yes")) {
                // delete employee from the list
                employees.remove(employeeId);
                System.out.println("Employee successfully fired.");
                System.out.println();
            } else {
                System.out.println("Employee not fired.");
                System.out.println();
            }
        } else {
            System.out.println("Employee with ID " + employeeId + " not found. Please try with another ID");
            System.out.println();
        }
    }

    @Override
    public List<Employee> listEmployees() {
        System.out.println("List of Employees:");
        System.out.println();

        //sort employees by ID
        List<Employee> sortedEmployees = new ArrayList<>(employees.values());
        sortedEmployees.sort(Comparator.comparing(e -> Integer.parseInt(e.getId())));

        if (sortedEmployees.isEmpty()) {
            System.out.println("No employees found");
            return null;
        } else {
            for (Employee employee: sortedEmployees) {
                displayEmployee(employee);
                System.out.println("-----------------------------");
            }
        }
        System.out.println();
        System.out.printf("Total number of employees: %d%n", employees.size());
        System.out.println();
        return new ArrayList<>(employees.values());
    }

    public void displayEmployee(Employee employee) {
        System.out.println("ID: " + employee.getId());
        System.out.println("First Name: " + employee.getFirstName());
        System.out.println("Last Name: " + employee.getLastName());
        System.out.println("Department: " + employee.getDepartment());
        System.out.println("Role: " + employee.getRole());
        System.out.println("Salary: " + employee.getSalary());
        System.out.println("Start date: " + employee.getStartDate());
    }

    @Override
    public List<Employee> searchByName() {
        // Enter name of employee you want to find
        String searchName = Validation.validateInput("Enter the name of the employee you want to find: ",
                new Validation.LettersAndSpaceValidator()).toLowerCase();
        List<Employee> foundEmployees = new ArrayList<>();
        // Check all employees and find those whose name match the request
        for (Employee employee : employees.values()) {
            String firstName = employee.getFirstName().toLowerCase();
            String lastName = employee.getLastName().toLowerCase();

            if (firstName.contains(searchName) || lastName.contains(searchName)) {
                foundEmployees.add(employee);
            }
        }
        if (foundEmployees.isEmpty()) {
            System.out.println("No employees found with the specified name.");
            System.out.println();
        } else {
            // display information about found employee
            displayFoundEmployees(foundEmployees);
        }

        return foundEmployees;
    }

    // display information about found employees
    public void displayFoundEmployees(List<Employee> foundEmployees) {
        //sort employees by ID
        List<Employee> sortedEmployees = new ArrayList<>(foundEmployees);
        sortedEmployees.sort(Comparator.comparing(e -> Integer.parseInt(e.getId())));
        if (sortedEmployees.size() == 1) {
            System.out.println("1 employee found:");
            System.out.println();
        } else {
            System.out.printf("%d employees found:%n", sortedEmployees.size());
            System.out.println();
        }

        for (Employee employee : sortedEmployees) {
            displayEmployee(employee);
            System.out.println("-----------------------------");
        }
        System.out.println("Search is over");
        System.out.println();
    }

    @Override
    public Employee searchById() {
        // Enter ID of the employee you want to find
        String employeeId = Validation.validateInput("Enter the ID of the employee you want to find (e.g. 0001 to 9999): ",
                new Validation.IdValidator());
        // check if employee with this ID exists
        if (employees.containsKey(employeeId)) {
            // get employee bi ID
            Employee foundEmployee = employees.get(employeeId);
            System.out.println();
            displayEmployee(foundEmployee);
            System.out.println("-----------------------------");
            System.out.println("Search is over");
            System.out.println();
            return foundEmployee;
        } else {
            System.out.println("Employee with ID " + employeeId + " not found. Please try with another ID");
            System.out.println();
            return null;  // Return null if employee not found
        }
    }

    @Override
    public List<Employee> searchByDepartment() {
        System.out.println("Input department of the employee you want to find: ");
        String searchDepartment = Validation.validateInput("Input department of the employee you want to find: ",
                new Validation.DepartmentValidator());
        List<Employee> foundDepartmentEmployees = new ArrayList<>();
        for (Employee employee : employees.values()) {
            String department = employee.getDepartment();

            if (department.equals(searchDepartment)) {
                foundDepartmentEmployees.add(employee);
            }
        }

        if (foundDepartmentEmployees.isEmpty()) {
            System.out.printf("No employees found in %s department%n", searchDepartment);
        } else {
            // display information about found employee
            displayFoundEmployees(foundDepartmentEmployees);
        }
        return foundDepartmentEmployees;
    }

    @Override
    public void saveData() {
        try {
            // Clear file before writing
            writer.close();
            new FileWriter("data.csv", false).close();

            writer = new BufferedWriter(new FileWriter("data.csv", true));
            for (Map.Entry<String, Employee> entry : employees.entrySet()) {
                Employee employee = entry.getValue();
                String line = String.format("%s,%s,%s,%s,%s,%.2f,%s",
                        employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getDepartment(),
                        employee.getRole(), employee.getSalary(), employee.getStartDate());
                writer.write(line);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving employee data: " + e.getMessage());
        }

    }

    @Override
    public HashMap<String, Employee> readData() {
        HashMap<String, Employee> employeeMap = new HashMap<>();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    String id = String.format("%04d", Integer.parseInt(data[0].trim()));
                    String firstName = data[1].trim();
                    String lastName = data[2].trim();
                    String department = data[3].trim();
                    String role = data[4].trim();
                    double salary = Double.parseDouble(data[5].trim());
                    String startDate = data[6].trim();

                    Employee employee = new Employee(id, firstName, lastName, department, role, salary, startDate);
                    employeeMap.put(id, employee);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading employee data: " + e.getMessage());
        }

        return employeeMap;
    }

}
