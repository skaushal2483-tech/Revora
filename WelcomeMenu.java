import java.util.Scanner;

public class WelcomeMenu {

    private Scanner scanner;

    public WelcomeMenu() {
        scanner = new Scanner(System.in);
    }

    public void displayWelcomePage() {

        int choice;

        do {

            System.out.println();
            System.out.println("============================================================");
            System.out.println("                         R E V O R A");
            System.out.println("                  YOUR MACHINE. OUR MASTERY.");
            System.out.println("============================================================");

            System.out.println("\n   1. About Us");
            System.out.println("   2. Enter REVORA");
            System.out.println("   3. Exit");

            System.out.print("\n   Enter your choice: ");

            try {

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        System.out.println("\n   About REVORA");
                        System.out.println("   Smart Vehicle Maintenance & Service Management System.");
                        pause();
                        break;

                    case 2:
                        System.out.println("\n   Welcome to REVORA!");
                        pause();
                        break;

                    case 3:
                        System.out.println("\n   Thank you for choosing REVORA.");
                        System.out.println("   Drive Safe. Stay Ahead.");
                        break;

                    default:
                        System.out.println("\n   Invalid choice! Please try again.");
                        pause();
                }

            } catch (NumberFormatException e) {

                System.out.println("\n   Invalid input! Enter a numeric choice.");
                pause();
                choice = 0;
            }

        } while (choice != 3);
    }

    private void pause() {

        System.out.print("\n   Press ENTER to continue...");
        scanner.nextLine();
    }
}