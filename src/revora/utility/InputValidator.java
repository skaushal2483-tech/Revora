package revora.utility;

import revora.exception.InvalidEmailException;
import revora.exception.InvalidPhoneException;
import revora.exception.InvalidVehicleNumberException;
import revora.exception.InvalidInputException;

public class InputValidator {

    // Validate email address
    public static void validateEmail(String email)
            throws InvalidEmailException {

        String regex =
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (email == null || !email.matches(regex)) {
            throw new InvalidEmailException("Invalid email format.");
        }
    }

    // Validate Indian mobile number
    public static void validatePhone(String phone)
            throws InvalidPhoneException {

        String regex = "[6-9][0-9]{9}";

        if (phone == null || !phone.matches(regex)) {
            throw new InvalidPhoneException(
                    "Enter a valid 10-digit Indian mobile number.");
        }
    }

    // Validate vehicle registration number
    public static void validateVehicleNumber(String vehicleNumber)
            throws InvalidVehicleNumberException {

        String regex =
                "^[A-Z]{2}[0-9]{1,2}[A-Z]{1,3}[0-9]{1,4}$";

        if (vehicleNumber == null
                || !vehicleNumber.toUpperCase().matches(regex)) {

            throw new InvalidVehicleNumberException(
                    "Invalid vehicle registration number.");
        }
    }

    // Validate name
    public static void validateName(String name, String fieldName)
            throws InvalidInputException {

        String regex = "[A-Za-z]+([ '-][A-Za-z]+)*";

        if (name == null || !name.matches(regex)) {
            throw new InvalidInputException(
                    "Invalid " + fieldName + ".");
        }
    }

    // Validate PIN code
    public static void validatePinCode(String pinCode)
            throws InvalidInputException {

        String regex = "[1-9][0-9]{5}";

        if (pinCode == null || !pinCode.matches(regex)) {
            throw new InvalidInputException(
                    "Enter a valid 6-digit PIN code.");
        }
    }

    // Validate password
    public static void validatePassword(String password)
            throws InvalidInputException {

        String regex =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";

        if (password == null || !password.matches(regex)) {
            throw new InvalidInputException(
                    "Password must contain at least 8 characters, "
                    + "one uppercase letter, one lowercase letter, "
                    + "one digit and one special character.");
        }
    }

    // Validate non-empty input
    public static void validateNotEmpty(String input, String fieldName)
            throws InvalidInputException {

        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(
                    fieldName + " cannot be empty.");
        }
    }

    // Validate positive numerical values
    public static void validatePositiveNumber(double number, String fieldName)
            throws InvalidInputException {

        if (!Double.isFinite(number) || number <= 0) {
            throw new InvalidInputException(
                    fieldName + " must be a positive number.");
        }
    }

    // Validate non-negative numerical values
    public static void validateNonNegativeNumber(double number, String fieldName)
            throws InvalidInputException {

        if (!Double.isFinite(number) || number < 0) {
            throw new InvalidInputException(
                    fieldName + " cannot be negative.");
        }
    }

    // Validate vehicle brand
    public static void validateBrand(String brand)
            throws InvalidInputException {

        String regex = "^[A-Za-z][A-Za-z .'-]{0,39}$";

        if (brand == null || !brand.matches(regex)) {
            throw new InvalidInputException("Invalid brand name.");
        }
    }

    // Validate vehicle model
    public static void validateModel(String model)
            throws InvalidInputException {

        String regex = "^[A-Za-z0-9][A-Za-z0-9 .'-]{0,39}$";

        if (model == null || !model.matches(regex)) {
            throw new InvalidInputException("Invalid model name.");
        }
    }
}