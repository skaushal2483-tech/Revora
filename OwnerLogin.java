package revora.ui.auth;

import java.util.Scanner;

import revora.model.Owner;
import revora.manager.OwnerManager;

public class OwnerLogin {

    private Scanner scanner;
    private OwnerManager ownerManager;

    public OwnerLogin(Scanner scanner, OwnerManager ownerManager) {
        this.scanner = scanner;
        this.ownerManager = ownerManager;
    }

    public Owner loginOwner() {

        while (true) {

            System.out.println();
            System.out.println("============================================================");
            System.out.println("                       OWNER LOGIN");
            System.out.println("============================================================");
            System.out.println("   Enter 0 at any time to return to the previous menu.");
            System.out.println();

            System.out.print("   Email: ");
            String email = scanner.nextLine().trim();

            if (email.equals("0")) {
                return null;
            }

            System.out.print("   Password: ");
            String password = scanner.nextLine();

            if (password.equals("0")) {
                return null;
            }

            Owner owner = ownerManager.findOwnerByEmail(email);

            if (owner != null
                    && owner.getPassword().equals(password)
                    && owner.isActive()) {

                System.out.println();
                System.out.println("============================================================");
                System.out.println("                     ACCESS GRANTED!");
                System.out.println("============================================================");
                System.out.println("   WELCOME BACK TO REVORA, " + owner.getFirstName() + "!");
                System.out.println("   YOUR MACHINE. OUR MASTERY.");
                System.out.println("============================================================");

                System.out.print("\n   Press ENTER to continue...");
                scanner.nextLine();

                return owner;

            } else {

                System.out.println();
                System.out.println("   INVALID EMAIL OR PASSWORD.");
                System.out.println("   PLEASE TRY AGAIN.");
                System.out.println();
                System.out.print("   Press ENTER to continue...");
                scanner.nextLine();
            }
        }
    }
}