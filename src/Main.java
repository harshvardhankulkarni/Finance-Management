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
        firstPage();
    }

    public static void firstPage() {
        System.out.println("First Page");
        while (true) {
            System.out.println();
            System.out.println("1: Signup Customer ");
            System.out.println("2: Login Customer ");
            System.out.println("3: Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            System.out.println();
            switch (choice) {
                case 1 -> {
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
                        System.out.println("Something went Wrong Try again!");
                        continue;
                    }
                    finance = new Finance(currentUser);
                    System.out.println();
                    System.out.println("Signup Successfully!!");
                    System.out.println();
                    secondPage();
                }
                case 2 -> {
                    System.out.print("Enter Username: ");
                    String username = sc.next();
                    System.out.print("Enter Password: ");
                    String password = sc.next();
                    try {
                        currentUser.login(username, password);
                    } catch (SQLException e) {
                        System.out.println("Something went Wrong Try again!");
                        continue;
                    }
                    finance = new Finance(currentUser);
                    System.out.println();
                    System.out.println("Login Successfully!!");
                    System.out.println();
                    secondPage();
                }
                default -> System.out.println("Thank You!!");
            }
            break;
        }
    }

    public static void secondPage() {
        System.out.println("Second Page");
        ArrayList<String> categories = category.getCategories();
        while (true) {
            System.out.println();
            System.out.println("1: Insert Today's Expense");
            System.out.println("2: View Today's Total Expense");
            System.out.println("3: View Today's Expense by category");
            System.out.println("4: View Total Expense by date");
            System.out.println("5: View Total Expense by date and category");
            System.out.println("6: View Total Expense by category");
            System.out.println("7: Logout");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            System.out.println();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Today's Expense: ");
                    double expense = sc.nextDouble();
                    for (int i = 0; i < categories.size(); i++) {
                        System.out.println((i + 1) + ": " + categories.get(i));
                    }
                    System.out.print("In which category you want add expense: ");
                    int categoryChoice = sc.nextInt();
                    finance.insertTodayExpense(expense, categoryChoice);
                    System.out.println();
                    System.out.println("Today's Expense added successfully in " + categories.get(categoryChoice));
                    System.out.println();
                    continue;
                }
                case 2 -> {
                    double totalExpense;
                    totalExpense = finance.viewExpense(todayDate);
                    System.out.println();
                    System.out.println("Today's Total Expense is " + totalExpense + "₹");
                    System.out.println();
                    continue;
                }
                case 3 -> {
                    for (int i = 0; i < categories.size(); i++) {
                        System.out.println((i + 1) + ": " + categories.get(i));
                    }
                    System.out.print("In which category you want add expense: ");
                    int categoryChoice = sc.nextInt();
                    double totalExpense;
                    totalExpense = finance.viewExpenseByCategory(todayDate, categoryChoice);
                    System.out.println();
                    System.out.println("Today's Total Expense in " + categories.get(categoryChoice) + " is " + totalExpense + "₹");
                    System.out.println();
                    continue;
                }
                case 4 -> {
                    System.out.print("Enter Date to view expense: ");
                    String viewDate = sc.next();
                    double totalExpense;
                    totalExpense = finance.viewExpense(viewDate);
                    System.out.println();
                    System.out.println(viewDate + " Total Expense is " + totalExpense + "₹");
                    System.out.println();
                    continue;
                }
                case 5 -> {
                    for (int i = 0; i < categories.size(); i++) {
                        System.out.println((i + 1) + ": " + categories.get(i));
                    }
                    System.out.print("In which category you want add expense: ");
                    int categoryChoice = sc.nextInt();
                    System.out.print("Enter Date: ");
                    String viewDate = sc.next();
                    double totalExpense;
                    totalExpense = finance.viewExpenseByCategory(viewDate, categoryChoice);
                    System.out.println();
                    System.out.println(viewDate + " Total Expense in " + categories.get(categoryChoice) + " is " + totalExpense + "₹");
                    System.out.println();
                    continue;
                }
                case 6 -> {
                    for (int i = 0; i < categories.size(); i++) {
                        System.out.println((i + 1) + ": " + categories.get(i));
                    }
                    System.out.print("In which category you want add expense: ");
                    int categoryChoice = sc.nextInt();
                    double totalExpense;
                    totalExpense = finance.viewTotalExpenseInCategory(categoryChoice);
                    System.out.println();
                    System.out.println("Total Expense in " + categories.get(categoryChoice) + " is " + totalExpense + "₹");
                    System.out.println();
                    continue;
                }
                default -> {
                    currentUser.logout();
                    System.out.println("User Logout!");
                    firstPage();
                }
            }
            break;
        }
    }
}
