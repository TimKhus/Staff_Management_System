public interface Validator {
    boolean isValid(String input);
    String getErrorMessage();
}
