public class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private String department;
    private String role;
    private double salary;
    private static int counter = 0;

    public Employee(String firstName, String lastName, String department, String role, double salary) {
        this.id = generateId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.role = role;
        this.salary = salary;
    }

    public Employee(String id, String firstName, String lastName, String department, String role, double salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.role = role;
        this.salary = salary;
    }

    private String generateId() {
        if (StaffService.getEmployees().size() > 0) {
            counter = StaffService.getEmployees().size();
        }
        counter++;
        id = String.format("%d", counter);
        return id;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    public String getRole() {
        return role;
    }

    public double getSalary() {
        return salary;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

}
