import java.io.*;
import java.util.Scanner;

public class StaffManagementApp {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("data.csv", "rw")) {
            BufferedReader reader = new BufferedReader(new FileReader(file.getFD()));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getFD()));

            Service service = new StaffService(reader, writer);
            Manager manager = new StaffManager(service);
            System.out.println("Welcome to Staff Management System!");
            System.out.println();

            Scanner sc = new Scanner(System.in);
            boolean isRunning = true;
            while (isRunning) {
                StaffManager.displayCommands();
                System.out.println("Enter command: ");
                String command = sc.nextLine().trim();
                if ("8".equals(command)) {
                    // Save & Exit
                    service.saveData();
                    isRunning = false;
                } else {
                    manager.execute(command);
                }
            }
        } catch (IOException e) {
            System.out.println("Error initializing the application: " + e.getMessage());
        }
    }

}
