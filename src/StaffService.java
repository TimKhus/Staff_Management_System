import java.io.*;
import java.util.*;

public class StaffService implements Service {
    private BufferedReader reader;
    private BufferedWriter writer;
    private static HashMap<String, Employee> employees;

    public StaffService(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.writer = writer;
        this.employees = readData();  // Read data from database file when application starts
    }

    public static HashMap<String, Employee> getEmployees() {
        return employees;
    }

    @Override
    public void addEmployee() {
        Scanner sc = new Scanner(System.in);
        String firstName, lastName, department, role;
        double salary;
        System.out.println("Enter employee details - First Name, Last Name, Department, Role, Salary: ");

        while (true) {
            System.out.println("First Name: ");
            String input = sc.nextLine().trim().toLowerCase();
            if (!input.isEmpty() && containsOnlyLettersAndSpaces(input) && containsNotOnlySpace(input)) {
                firstName = capitalizeFirstLetter(input);
                break;
            } else {
                System.out.println("Wrong input");
            }
        }

        while (true) {
            System.out.println("Last Name: ");
            String input = sc.nextLine().trim().toLowerCase();

            if (!input.isEmpty() && containsOnlyLettersAndSpaces(input) && containsNotOnlySpace(input)) {
                lastName = capitalizeFirstLetter(input);
                break;
            } else {
                System.out.println("Wrong input");
            }
        }

        while (true) {
            System.out.println("Choose department:");
            printDepartments();
            String input = sc.nextLine().trim();
            if (isValidDepartment(input)) {
                Department selectedDepartment = Department.valueOf(input.toUpperCase());
                department = selectedDepartment.toString();
                break;
            } else {
                System.out.println("Invalid department. Please enter a valid department.");
            }
        }

        while (true) {
            System.out.println("Role: ");
            String input = sc.nextLine().trim().toLowerCase();

            if (!input.isEmpty() && containsOnlyLettersAndSpaces(input) && containsNotOnlySpace(input)) {
                role = capitalizeFirstLetter(input);
                break;
            } else {
                System.out.println("Wrong input");
            }
        }

        while (true) {
            System.out.println("Salary: ");
            String input = sc.nextLine().trim();

            if (!input.isEmpty() && isNumeric(input)) {
                salary = Double.parseDouble(input);
                break;
            }
            else {
                System.out.println("Wrong input");
            }
        }

        Employee newEmployee = new Employee(firstName, lastName, department, role, salary);
        employees.put(newEmployee.getId(), newEmployee);
    }

    private boolean isNumeric(String input) {
        return input.matches("\\d+");
    }

    private boolean isValidDepartment(String input) {
        try {
            Department.valueOf(input.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private void printDepartments() {
        for (Department department : Department.values()) {
            System.out.println(department);
        }
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private boolean containsOnlyLettersAndSpaces(String input) {
        return input.matches("[a-zA-Z ]+");
    }

    private boolean containsNotOnlySpace(String input) {
        return input.matches(".*\\S.*");
    }

    @Override
    public void editEmployee() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the ID of the employee you want to edit: ");
        String employeeId = sc.nextLine().trim();
        if (employees.containsKey(employeeId)) {
            // get employee by ID
            Employee employeeToEdit = employees.get(employeeId);
            // Request new data from the user
            System.out.println("Enter new details for the employee (or press Enter to keep existing values):");
            System.out.print("First Name [" + employeeToEdit.getFirstName() + "]: ");
            String newFirstName = sc.nextLine().trim().toLowerCase();
            if (!newFirstName.isEmpty()) {
                   if (containsOnlyLettersAndSpaces(newFirstName) && containsNotOnlySpace(newFirstName)) {
                       employeeToEdit.setFirstName(capitalizeFirstLetter(newFirstName));
                   } else {
                       System.out.println("Wrong input. First name not changed");
                   }
            }

            System.out.print("Last Name [" + employeeToEdit.getLastName() + "]: ");
            String newLastName = sc.nextLine().trim().toLowerCase();
            if (!newLastName.isEmpty()) {
                if (containsOnlyLettersAndSpaces(newLastName) && containsNotOnlySpace(newLastName)) {
                    employeeToEdit.setLastName(capitalizeFirstLetter(newLastName));
                } else {
                    System.out.println("Wrong input. Last name not changed");
                }
            }

            System.out.print("Department [" + employeeToEdit.getDepartment() + "]");
            System.out.println("Choose new department:");
            printDepartments();
            String input = sc.nextLine().trim();
            if (isValidDepartment(input)) {
                Department selectedDepartment = Department.valueOf(input.toUpperCase());
                String newDepartment = selectedDepartment.toString();
                employeeToEdit.setDepartment(newDepartment);
            } else {
                System.out.println("Wrong input. Department not changed");
            }

            System.out.print("Role [" + employeeToEdit.getRole() + "]: ");
            String newRole = sc.nextLine().trim().toLowerCase();
            if (!newRole.isEmpty()) {
                if (containsOnlyLettersAndSpaces(newRole) && containsNotOnlySpace(newRole)) {
                    employeeToEdit.setRole(capitalizeFirstLetter(newRole));
                } else {
                    System.out.println("Wrong input. Role not changed");
                }
            }

            System.out.print("Salary [" + employeeToEdit.getSalary() + "]: ");
            String newSalary = sc.nextLine().trim();
            if (!newSalary.isEmpty()) {
                if (isNumeric(newSalary)) {
                    double salary = Double.parseDouble(newSalary);
                    employeeToEdit.setSalary(salary);
                } else {
                    System.out.println("Wrong input. Salary not changed");
                }
            }
            // notify user that the employee has been successfully edited
            System.out.println("Employee edited successfully.");
        } else {
            System.out.println("Employee with ID " + employeeId + " not found.");
        }
    }

    @Override
    public void fireEmployee() {
        Scanner sc = new Scanner(System.in);

        // Enter ID of the employee you want to fire
        System.out.print("Enter ID of the employee you want to fire: ");
        String employeeId = sc.nextLine().trim();

        // check if employee with this ID exist
        if (employees.containsKey(employeeId)) {
            // get employee by ID
            Employee employeeToFire = employees.get(employeeId);

            // display information about the employee before fire
            System.out.println("Firing the following employee:");
            displayEmployee(employeeToFire);

            // Confirm you really want to fire employee
            System.out.print("Are you sure you want to fire this employee? (yes/no): ");
            String confirmation = sc.nextLine().trim().toLowerCase();

            if (confirmation.equals("yes")) {
                // delete employee from the list
                employees.remove(employeeId);
                System.out.println("Employee successfully fired.");
            } else {
                System.out.println("Employee not fired.");
            }
        } else {
            System.out.println("Employee with ID " + employeeId + " not found.");
        }
    }

    @Override
    public List<Employee> listEmployees() {
        System.out.println("List of Employees:");

        if (employees == null || employees.isEmpty()) {
            System.out.println("No employees found");
            return null;
        } else {
            for (Map.Entry<String, Employee> entry : employees.entrySet()) {
                Employee employee = entry.getValue();
                displayEmployee(employee);
                System.out.println("-----------------------------");
            }
        }
        return new ArrayList<>(employees.values());
    }

    public void displayEmployee(Employee employee) {
        System.out.println("ID: " + employee.getId());
        System.out.println("First Name: " + employee.getFirstName());
        System.out.println("Last Name: " + employee.getLastName());
        System.out.println("Department: " + employee.getDepartment());
        System.out.println("Role: " + employee.getRole());
        System.out.println("Salary: " + employee.getSalary());
    }

    @Override
    public List<Employee> searchByName() {
        Scanner sc = new Scanner(System.in);
        // Enter name of employee you want to find
        System.out.println("Enter the name of the employee you want to find: ");
        String searchName = sc.nextLine().trim().toLowerCase();
        List<Employee> foundEmployees = new ArrayList<>();
        // Check all employees and find those whose name match the request
        for (Employee employee : employees.values()) {
            String firstName = employee.getFirstName().toLowerCase();
            String lastName = employee.getLastName().toLowerCase();

            if (firstName.contains(searchName) || lastName.contains(searchName)) {
                foundEmployees.add(employee);
                // display information about found employee
                System.out.println("Employee found:");
                displayEmployee(employee);
            }
        }
        if (foundEmployees.isEmpty()) {
            System.out.println("No employees found with the specified name.");
        }

        return foundEmployees;
    }

    @Override
    public Employee searchById() {
        Scanner sc = new Scanner(System.in);
        // Enter ID of the employee you want to find
        System.out.println("Enter the ID of the employee you want to find: ");
        String employeeId = sc.nextLine().trim();
        // check if employee with this ID exists
        if (employees.containsKey(employeeId)) {
            // get employee bi ID
            Employee foundEmployee = employees.get(employeeId);
            displayEmployee(foundEmployee);
            return foundEmployee;
        } else {
            System.out.println("Employee with ID " + employeeId + " not found.");
            return null;  // Return null if employee not found
        }
    }

    @Override
    public List<Employee> searchByDepartment() {
        Scanner sc = new Scanner(System.in);
        // Enter name of employee you want to find
        System.out.println("Input department of the employee you want to find: ");
        printDepartments();
        String searchDepartment = sc.nextLine().trim().toLowerCase();
        List<Employee> foundEmployees = new ArrayList<>();
        if (isValidDepartment(searchDepartment)) {
            // Check all employees and find those whose name match the request
            for (Employee employee : employees.values()) {
                String department = employee.getDepartment().toLowerCase();

                if (department.equals(searchDepartment)) {
                    foundEmployees.add(employee);
                    // display information about found employee
                    System.out.println("Employee found:");
                    displayEmployee(employee);
                }
            }
        } else {
            System.out.println("No such department in the company");
        }
        if (foundEmployees.isEmpty()) {
            System.out.println("No employees found with the specified name.");
        }
        return foundEmployees;
    }

    @Override
    public void saveData() {
        try  {
            // Clear file before writing
            writer.close();
            new FileWriter("data.csv", false).close();

            writer = new BufferedWriter(new FileWriter("data.csv", true));
            for (Map.Entry<String, Employee> entry : employees.entrySet()) {
                Employee employee = entry.getValue();
                String line = String.format("%s,%s,%s,%s,%s,%.2f",
                        employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getDepartment(),
                        employee.getRole(), employee.getSalary());
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
                if (data.length == 6) {
                    String id = data[0].trim();
                    String firstName = data[1].trim();
                    String lastName = data[2].trim();
                    String department = data[3].trim();
                    String role = data[4].trim();
                    double salary = Double.parseDouble(data[5].trim());

                    Employee employee = new Employee(id, firstName, lastName, department, role, salary);
                    employeeMap.put(id, employee);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading employee data: " + e.getMessage());
        }

        return employeeMap;
    }
}
