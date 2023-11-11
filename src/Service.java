import java.util.HashMap;
import java.util.List;

public interface Service {
    void addEmployee();
    void editEmployee();
    void fireEmployee();
    List<Employee> listEmployees();
    List<Employee> searchByName();
    Employee searchById();
    List<Employee> searchByDepartment();
    void saveData();
    HashMap<String, Employee> readData();
}
