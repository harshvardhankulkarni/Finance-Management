import com.database.Category;
import com.database.Finance;
import com.database.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-M-yyyy"));
    private static Category category;
    private static User currentUser;
    private static Finance finance;

    public static void main(String[] args) {
        category = new Category();
        currentUser = new User();
        clearScreen();
        firstPage();
    }

    public static void firstPage() {
        while (true) {
            centerString("1: Login Customer ");
            centerString("2: Signup Customer ");
            centerString("3: Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            System.out.println();
            switch (choice) {
                case 2 -> {
                    System.out.print("Enter First Name: ");
                    String first_name = sc.next();
                    System.out.print("Enter Last Name: ");
                    String last_name = sc.next();
                    System.out.print("Enter Username: ");
                    String username = sc.next();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    System.out.print("Enter Phone Number: ");
                    String phone_number = sc.next();
                    System.out.print("Enter Password: ");
                    String password = sc.next();
                    try {
                        currentUser.signup(first_name, last_name, age, phone_number, username, password);
                    } catch (SQLException e) {
                        centerString("Something went Wrong Try again!");
                        continue;
                    }
                    finance = new Finance(currentUser);
                    clearScreen();
                    centerString("Signup Successfully!!");
                    secondPage();
                }
                case 1 -> {
                    System.out.print("Enter Username: ");
                    String username = sc.next();
                    System.out.print("Enter Password: ");
                    String password = sc.next();
                    try {
                        currentUser.login(username, password);
                    } catch (SQLException e) {
                        centerString("Something went Wrong Try again!");
                        continue;
                    }
                    finance = new Finance(currentUser);
                    clearScreen();
                    centerString("Login Successfully!!");
                    secondPage();
                }
                default -> centerString("Thank You!!");
            }
            break;
        }
    }

    public static void secondPage() {
        ArrayList<String> categories = category.getCategories();
        while (true) {
            System.out.println();
            centerString("1: Insert Today's Expense");
            centerString("2: View Today's Total Expense");
            centerString("3: View Today's Expense by category");
            centerString("4: View Total Expense by date");
            centerString("5: View Total Expense by date and category");
            centerString("6: View Total Expense by category");
            centerString("7: Logout");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            System.out.println();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Today's Expense: ");
                    double expense = sc.nextDouble();
                    for (int i = 0; i < categories.size(); i++) {
                        centerString((i + 1) + ": " + categories.get(i));
                    }
                    System.out.print("In which category you want add expense: ");
                    int categoryChoice = sc.nextInt();
                    finance.insertTodayExpense(expense, categoryChoice - 1);
                    clearScreen();
                    centerString("Today's Expense added successfully in " + categories.get(categoryChoice - 1));
                    continue;
                }
                case 2 -> {
                    double totalExpense;
                    totalExpense = finance.viewExpense(todayDate);
                    clearScreen();
                    centerString("Today's Total Expense is " + totalExpense + "₹");
                    continue;
                }
                case 3 -> {
                    for (int i = 0; i < categories.size(); i++) {
                        centerString((i + 1) + ": " + categories.get(i));
                    }
                    System.out.print("In which category you want add expense: ");
                    int categoryChoice = sc.nextInt();
                    double totalExpense;
                    totalExpense = finance.viewExpenseByCategory(todayDate, categoryChoice - 1);
                    clearScreen();
                    centerString("Today's Total Expense in " + categories.get(categoryChoice - 1) + " is " + totalExpense + "₹");
                    continue;
                }
                case 4 -> {
                    System.out.print("Enter Date to view expense: ");
                    String viewDate = sc.next();
                    double totalExpense;
                    totalExpense = finance.viewExpense(viewDate);
                    clearScreen();
                    centerString(viewDate + " Total Expense is " + totalExpense + "₹");
                    continue;
                }
                case 5 -> {
                    for (int i = 0; i < categories.size(); i++) {
                        centerString((i + 1) + ": " + categories.get(i));
                    }
                    System.out.print("In which category you want add expense: ");
                    int categoryChoice = sc.nextInt();
                    System.out.print("Enter Date: ");
                    String viewDate = sc.next();
                    double totalExpense;
                    totalExpense = finance.viewExpenseByCategory(viewDate, categoryChoice - 1);
                    clearScreen();
                    centerString(viewDate + " Total Expense in " + categories.get(categoryChoice - 1) + " is " + totalExpense + "₹");
                    continue;
                }
                case 6 -> {
                    for (int i = 0; i < categories.size(); i++) {
                        centerString((i + 1) + ": " + categories.get(i));
                    }
                    System.out.print("In which category you want add expense: ");
                    int categoryChoice = sc.nextInt();
                    double totalExpense;
                    totalExpense = finance.viewTotalExpenseInCategory(categoryChoice - 1);
                    clearScreen();
                    centerString("Total Expense in " + categories.get(categoryChoice - 1) + " is " + totalExpense + "₹");
                    continue;
                }
                default -> {
                    currentUser.logout();
                    clearScreen();
                    centerString("User Logout!");
                    firstPage();
                }
            }
            break;
        }
    }

    public static void centerString(String text) {
        int consoleWidth = 154; // Change this to the width of your console

        int padding = (consoleWidth - text.length()) / 2;

        String centeredText = " ".repeat(padding) + text;

        System.out.println(centeredText);
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        centerString("Personal Finance Manager\n");
    }
}
