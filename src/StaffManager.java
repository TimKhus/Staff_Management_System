public class StaffManager implements Manager {
    private final Service service;

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
            default -> System.out.printf("Wrong command%n%n");
        }
    }

    public static void displayCommands() {
        System.out.println("Available Commands:");
        System.out.println("1 - Add Employee");
        System.out.println("2 - Edit Employee");
        System.out.println("3 - Fire Employee");
        System.out.println("4 - List Employees");
        System.out.println("5 - Search by Name");
        System.out.println("6 - Search by ID");
        System.out.println("7 - Search by Department");
        System.out.println("8 - Save & Exit");
        System.out.println();
    }
}
