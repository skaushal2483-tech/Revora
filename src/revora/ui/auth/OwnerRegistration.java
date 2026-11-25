package revora.ui.auth;

import java.util.Scanner;

import revora.model.Owner;
import revora.manager.OwnerManager;

import revora.utility.InputValidator;
import revora.exception.InvalidEmailException;
import revora.exception.InvalidPhoneException;
import revora.exception.InvalidInputException;

public class OwnerRegistration {

    private Scanner scanner;
    private OwnerManager ownerManager;

    public OwnerRegistration(Scanner scanner, OwnerManager ownerManager) {
        this.scanner = scanner;
        this.ownerManager = ownerManager;
    }

    public void registerOwner() {

        System.out.println("\n============================================================");
        System.out.println("                    OWNER REGISTRATION");
        System.out.println("============================================================");

        // First Name
        String firstName = getValidName("Enter First Name: ", "first name");

        // Last Name
        String lastName = getValidName("Enter Last Name: ", "last name");

        // Email
        String email;

        while (true) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine().trim().toLowerCase();

            try {
                InputValidator.validateEmail(email);

                if (ownerManager.findOwnerByEmail(email) != null) {
                    System.out.println("Email already registered. Try another.");
                } else {
                    break;
                }

            } catch (InvalidEmailException e) {
                System.out.println(e.getMessage() + " Try again.");
            }
        }

        // Phone Number
        String phoneNumber;

        while (true) {
            System.out.print("Enter Phone Number: ");
            phoneNumber = scanner.nextLine().trim();

            try {
                InputValidator.validatePhone(phoneNumber);

                if (ownerManager.findOwnerByPhone(phoneNumber) != null) {
                    System.out.println("Phone number already registered. Try another.");
                } else {
                    break;
                }

            } catch (InvalidPhoneException e) {
                System.out.println(e.getMessage());
            }
        }

        // City
        String city = getValidName("Enter City: ", "city name");

        // PIN Code
        String pinCode;

        while (true) {
            System.out.print("Enter PIN Code: ");
            pinCode = scanner.nextLine().trim();

            try {
                InputValidator.validatePinCode(pinCode);
                break;

            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }

        // Password
        String password;

        while (true) {
            System.out.print("Create Password: ");
            password = scanner.nextLine();

            try {
                InputValidator.validatePassword(password);
                break;

            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }

        // Confirm Password
        String confirmPassword;

        while (true) {
            System.out.print("Confirm Password: ");
            confirmPassword = scanner.nextLine();

            if (!password.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Try again.");
            } else {
                break;
            }
        }

        // Create Owner
        String ownerId = ownerManager.generateOwnerId();

        Owner owner = new Owner(
                ownerId,
                firstName,
                lastName,
                email,
                phoneNumber,
                city,
                pinCode,
                password
        );

        if (ownerManager.addOwner(owner)) {

            System.out.println("\n============================================================");
            System.out.println("              REGISTRATION SUCCESSFUL!");
            System.out.println("============================================================");
            System.out.println("WELCOME TO THE REVORA FAMILY.");
            System.out.println("YOUR JOURNEY TO SMARTER VEHICLE CARE STARTS NOW.");
            System.out.println();
            System.out.println("YOUR OWNER ID: " + ownerId);
            System.out.println("YOUR MACHINE. OUR MASTERY.");
            System.out.println("============================================================");

        } else {
            System.out.println("\nRegistration failed. Account details already exist.");
        }

        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }

    // Reusable name validation
    private String getValidName(String message, String fieldName) {

        String input;

        while (true) {
            System.out.print(message);
            input = scanner.nextLine().trim();

            try {
                InputValidator.validateName(input, fieldName);
                return input;

            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}