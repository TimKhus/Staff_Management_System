import java.util.List;

public class StaffManager implements Manager {
    private Service service;

    public StaffManager(Service service) {
        this.service = service;
    }

    @Override
    public void execute(String command) {
        switch (command) {
            case "1" -> service.addEmployee();
            case "2" -> service.editEmployee();
            case "3" -> service.fireEmployee();
            case "4" -> service.listEmployees();
            case "5" -> service.searchByName();
            case "6" -> service.searchById();
            case "7" -> service.searchByDepartment();
            case "8" -> service.saveData();
        }
    }

}
