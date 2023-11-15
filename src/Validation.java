import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Validation {

    //validation to check letters and spaces
    static class LettersAndSpaceValidator implements Validator {
        @Override
        public boolean isValid(String input) {
            return input.matches("[a-zA-Z ]+") && input.matches(".*\\S.*");
        }

        @Override
        public String getErrorMessage() {
            return "Please enter valid input using letters and spaces";
        }
    }

    //validation to check digits
    static class DigitsValidator implements Validator {
        @Override
        public boolean isValid(String input) {
            return input.matches("\\d+");
        }

        @Override
        public String getErrorMessage() {
            return "Please enter valid input using numbers.";
        }
    }

    //validation of departments
    public static class DepartmentValidator implements Validator {
        public DepartmentValidator() {
            printDepartments();
        }

        @Override
        public boolean isValid(String input) {
            try {
                Department.valueOf(capitalizeFirstLetter(input));
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        @Override
        public String getErrorMessage() {
            printDepartments();
            return "Please enter a valid department from the list";
        }

        private void printDepartments() {
            System.out.println("Departments : ");
            for (Department department : Department.values()) {
                System.out.println(department);
            }
            System.out.println();
        }
    }

    //validation of ID
    public static class IdValidator implements Validator {
        @Override
        public boolean isValid(String input) {
            return input.matches("\\d{4}");
        }

        @Override
        public String getErrorMessage() {
            return "Please enter valid ID (e.g. 0001 to 9999)";
        }
    }

    //validation of dates
    public static class DateValidator implements Validator {
        @Override
        public boolean isValid(String input) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            try {
                LocalDate startDate = LocalDate.parse(input, formatter);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        public String getErrorMessage() {
            return "Please enter valid date in format dd.MM.yyyy";
        }
    }

    public static String validateInput(String prompt, Validator validator) {
        Scanner sc = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println(prompt);
            input = sc.nextLine().trim().toLowerCase();

            if (!input.isEmpty() && validator.isValid(input)) {
                return capitalizeFirstLetter(input);
            } else {
                System.out.println("Invalid input. " + validator.getErrorMessage());
            }
        }
    }


    public static String validateInput(String prompt, Validator validator, boolean couldBeEmpty) {
        Scanner sc = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println(prompt);
            input = sc.nextLine().trim().toLowerCase();

            if (input.isEmpty() && couldBeEmpty) {
                System.out.println("Data not changed");
                return input;
            }

            if (!input.isEmpty() && validator.isValid(input)) {
                return capitalizeFirstLetter(input);
            } else {
                System.out.println("Invalid input. " + validator.getErrorMessage());
                System.out.println("Data not changed");
            }
        }
    }

    private static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

}
