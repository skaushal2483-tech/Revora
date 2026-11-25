package revora.ui.owner.profile;

import java.util.Scanner;

import revora.model.Owner;
import revora.model.Vehicle;

import revora.manager.OwnerManager;
import revora.manager.VehicleManager;
import revora.manager.ServiceManager;
import revora.manager.OdometerManager;
import revora.manager.FuelManager;

import revora.utility.InputValidator;

import revora.exception.InvalidPhoneException;
import revora.exception.InvalidInputException;

public class OwnerProfile {

    private Scanner scanner;
    private Owner owner;

    private OwnerManager ownerManager;
    private VehicleManager vehicleManager;
    private ServiceManager serviceManager;
    private OdometerManager odometerManager;
    private FuelManager fuelManager;

    public OwnerProfile(
            Scanner scanner,
            Owner owner,
            OwnerManager ownerManager,
            VehicleManager vehicleManager,
            ServiceManager serviceManager,
            OdometerManager odometerManager,
            FuelManager fuelManager) {

        this.scanner = scanner;
        this.owner = owner;
        this.ownerManager = ownerManager;
        this.vehicleManager = vehicleManager;
        this.serviceManager = serviceManager;
        this.odometerManager = odometerManager;
        this.fuelManager = fuelManager;
    }

    // Main Profile Screen
    public void displayProfile() {

        int choice = 0;

        do {
            System.out.println();
            System.out.println("============================================================");
            System.out.println("                       MY PROFILE");
            System.out.println("============================================================");
            System.out.println("  OWNER ID       : " + owner.getOwnerId());
            System.out.println("  FIRST NAME     : " + owner.getFirstName());
            System.out.println("  LAST NAME      : " + owner.getLastName());
            System.out.println("  EMAIL          : " + owner.getEmail());
            System.out.println("  PHONE NUMBER   : " + owner.getPhoneNumber());
            System.out.println("  CITY           : " + owner.getCity());
            System.out.println("  PIN CODE       : " + owner.getPinCode());
            System.out.println("  ACCOUNT STATUS : "
                    + (owner.isActive() ? "ACTIVE" : "INACTIVE"));
            System.out.println("============================================================");
            System.out.println("  1. Edit Profile");
            System.out.println("  2. Delete Account");
            System.out.println("  3. Exit");
            System.out.println("============================================================");

            System.out.print("  Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        editProfile();
                        break;

                    case 2:
                        if (deleteAccount()) {
                            return;
                        }
                        break;

                    case 3:
                        System.out.println("\n  Returning to Dashboard...");
                        break;

                    default:
                        System.out.println("\n  Invalid choice!");
                        pause();
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "\n  Please enter a valid numeric choice.");
                pause();
            }

        } while (choice != 3);
    }

    // Edit Profile Menu
    private void editProfile() {

        int choice = 0;

        do {
            System.out.println();
            System.out.println("============================================================");
            System.out.println("                     EDIT PROFILE");
            System.out.println("============================================================");
            System.out.println("  1. Edit First Name");
            System.out.println("  2. Edit Last Name");
            System.out.println("  3. Edit Phone Number");
            System.out.println("  4. Edit City");
            System.out.println("  5. Edit PIN Code");
            System.out.println("  6. Change Password");
            System.out.println("  7. Exit");
            System.out.println("============================================================");

            System.out.print("  Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        editFirstName();
                        break;

                    case 2:
                        editLastName();
                        break;

                    case 3:
                        editPhoneNumber();
                        break;

                    case 4:
                        editCity();
                        break;

                    case 5:
                        editPinCode();
                        break;

                    case 6:
                        changePassword();
                        break;

                    case 7:
                        System.out.println(
                                "\n  Returning to My Profile...");
                        break;

                    default:
                        System.out.println("\n  Invalid choice!");
                        pause();
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "\n  Please enter a valid numeric choice.");
                pause();
            }

        } while (choice != 7);
    }

    // Edit First Name
    private void editFirstName() {

        System.out.println(
                "\n  CURRENT FIRST NAME: " + owner.getFirstName());

        String firstName = getValidName(
                "  Enter New First Name: ", "First Name");

        saveChanges();

        owner.setFirstName(firstName);

        System.out.println("\n  First name updated successfully!");
        pause();
    }

    // Edit Last Name
    private void editLastName() {

        System.out.println(
                "\n  CURRENT LAST NAME: " + owner.getLastName());

        String lastName = getValidName(
                "  Enter New Last Name: ", "Last Name");

        saveChanges();

        owner.setLastName(lastName);

        System.out.println("\n  Last name updated successfully!");
        pause();
    }

    // Edit Phone Number
    private void editPhoneNumber() {

        System.out.println(
                "\n  CURRENT PHONE NUMBER: " + owner.getPhoneNumber());

        String phone;

        while (true) {

            System.out.print("  Enter New Phone Number: ");
            phone = scanner.nextLine().trim();

            try {
                InputValidator.validatePhone(phone);

            } catch (InvalidPhoneException e) {
                System.out.println("  " + e.getMessage());
                continue;
            }

            Owner existingOwner =
                    ownerManager.findOwnerByPhone(phone);

            if (existingOwner != null
                    && !existingOwner.getOwnerId()
                            .equals(owner.getOwnerId())) {

                System.out.println(
                        "  Phone number already registered.");
                continue;
            }

            break;
        }

        saveChanges();

        owner.setPhoneNumber(phone);

        System.out.println("\n  Phone number updated successfully!");
        pause();
    }

    // Edit City
    private void editCity() {

        System.out.println("\n  CURRENT CITY: " + owner.getCity());

        String city = getValidName(
                "  Enter New City: ", "City");

        saveChanges();

        owner.setCity(city);

        System.out.println("\n  City updated successfully!");
        pause();
    }

    // Edit PIN Code
    private void editPinCode() {

        System.out.println(
                "\n  CURRENT PIN CODE: " + owner.getPinCode());

        String pinCode;

        while (true) {

            System.out.print("  Enter New PIN Code: ");
            pinCode = scanner.nextLine().trim();

            try {
                InputValidator.validatePinCode(pinCode);
                break;

            } catch (InvalidInputException e) {
                System.out.println("  " + e.getMessage());
            }
        }

        saveChanges();

        owner.setPinCode(pinCode);

        System.out.println("\n  PIN code updated successfully!");
        pause();
    }

    // Change Password
    private void changePassword() {

        System.out.println("\n============================================================");
        System.out.println("                     CHANGE PASSWORD");
        System.out.println("============================================================");

        System.out.print("  Enter Current Password: ");
        String currentPassword = scanner.nextLine();

        if (!owner.getPassword().equals(currentPassword)) {
            System.out.println("\n  Incorrect current password.");
            pause();
            return;
        }

        String newPassword;

        while (true) {

            System.out.print("  Enter New Password: ");
            newPassword = scanner.nextLine();

            try {
                InputValidator.validatePassword(newPassword);

            } catch (InvalidInputException e) {
                System.out.println("\n  " + e.getMessage());
                continue;
            }

            if (newPassword.equals(currentPassword)) {
                System.out.println(
                        "\n  New password must differ from current password.");
                continue;
            }

            break;
        }

        String confirmPassword;

        while (true) {

            System.out.print("  Confirm New Password: ");
            confirmPassword = scanner.nextLine();

            if (newPassword.equals(confirmPassword)) {
                break;
            }

            System.out.println(
                    "  Passwords do not match. Try again.");
        }

        saveChanges();

        owner.setPassword(newPassword);

        System.out.println("\n  Password changed successfully!");
        pause();
    }

    // Delete Owner Account with Cascading Deletion
    private boolean deleteAccount() {

        System.out.println("\n============================================================");
        System.out.println("                     DELETE ACCOUNT");
        System.out.println("============================================================");

        System.out.println("\n  WARNING: This action is permanent!");
        System.out.println("  - Your owner account will be deleted.");
        System.out.println("  - All your registered vehicles will be deleted.");
        System.out.println("  - All service requests will be deleted.");
        System.out.println("  - All odometer records will be deleted.");
        System.out.println("  - All fuel records will be deleted.");
        System.out.println("  - This action cannot be undone.");

        System.out.print("\n  Enter Your Password to Continue: ");
        String password = scanner.nextLine();

        if (!owner.getPassword().equals(password)) {
            System.out.println(
                    "\n  Incorrect password. Account deletion cancelled.");
            pause();
            return false;
        }

        System.out.print(
                "\n  Are you sure you want to delete your account? (Y/N): ");

        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("N")) {
            System.out.println("\n  Account deletion cancelled.");
            pause();
            return false;
        }

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println(
                    "\n  Invalid confirmation. Account deletion cancelled.");
            pause();
            return false;
        }

        String ownerId = owner.getOwnerId();

        boolean deleted = ownerManager.deleteOwner(ownerId);

        if (deleted) {

            // Remove all service requests belonging to the owner
            serviceManager.removeRequestsByOwner(ownerId);

            // Remove odometer and fuel records for owned vehicles
            for (Vehicle vehicle :
                    vehicleManager.getVehiclesByOwner(ownerId)) {

                String vehicleId = vehicle.getVehicleId();

                odometerManager.removeRecordsByVehicle(vehicleId);
                fuelManager.removeRecordsByVehicle(vehicleId);
            }

            // Remove all vehicles belonging to the owner
            vehicleManager.removeVehiclesByOwner(ownerId);

            System.out.println(
                    "\n  Your account and all associated records have been deleted.");

            System.out.println(
                    "  Thank you for choosing REVORA!");

            System.out.println(
                    "\n  Returning to Welcome Menu...");

            return true;
        }

        System.out.println(
                "\n  Unable to delete account. Please try again.");

        pause();

        return false;
    }

    // Validate Name
    private String getValidName(
            String message, String fieldName) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                InputValidator.validateName(input, fieldName);
                return input;

            } catch (InvalidInputException e) {
                System.out.println("  " + e.getMessage());
            }
        }
    }

    // Save Confirmation
    private void saveChanges() {
        System.out.print(
                "\n  Press ENTER to save changes...");
        scanner.nextLine();
    }

    // Pause Screen
    private void pause() {
        System.out.print(
                "\n  Press ENTER to continue...");
        scanner.nextLine();
    }
}