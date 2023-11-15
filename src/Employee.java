public class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private String department;
    private String role;
    private double salary;
    private String startDate;
    private static int counter = 0;

    public Employee(String firstName, String lastName, String department, String role, double salary, String startDate) {
        this.id = generateId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.role = role;
        this.salary = salary;
        this.startDate = startDate;
    }

    public Employee(String id, String firstName, String lastName, String department, String role, double salary,
                    String startDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.role = role;
        this.salary = salary;
        this.startDate = startDate;
    }

    private String generateId() {
        if (!StaffService.getEmployees().isEmpty()) {
            int maxKey = StaffService.getEmployees().keySet()
                    .stream()
                    .mapToInt(Integer::parseInt)
                    .max()
                    .orElse(0); // or handle the case where the set is empty

            id = String.format("%04d", maxKey + 1);
        } else {
            id = String.format("%04d", ++counter);
        }
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

    public String getStartDate() {
        return startDate;
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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

}
