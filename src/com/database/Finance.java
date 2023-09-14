package com.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Finance extends Database {
    private final User currentUser;

    public Finance(User user) {
        currentUser = user;
        try {
            Statement statement = getStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS \"finance\" (" + "\t\"finance_id\"\tINTEGER," + "\t\"user_id\"\tINTEGER," + "\t\"date\"\tdatetime NOT NULL," + "\t\"expense\"\tNUMERIC NOT NULL DEFAULT 0," + "\t\"expense_category_id\"\tINTEGER NOT NULL," + "\tFOREIGN KEY(\"expense_category_id\") REFERENCES \"expense_types\"(\"catagary_id\")," + "\tFOREIGN KEY(\"user_id\") REFERENCES \"user\"(\"user_id\")," + "\tPRIMARY KEY(\"finance_id\" AUTOINCREMENT)" + ")");
            statement.close();
        } catch (SQLException e) {
            // ignore
        }
    }

    public void insertTodayExpense(double expense, int category) {
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-M-yyyy"));
        try {
            if (currentUser.isLogin()) {
                Statement statement = getStatement();
                ResultSet resultSet = statement.executeQuery("SELECT \"expense\" FROM finance" + " WHERE \"date\"==\"" + todayDate + "\" AND \"user_id\"==\"" + currentUser.getUserID() + "\" AND \"expense_category_id\"==\"" + category + "\"");
                double oldExpense = resultSet.getDouble(1);
                statement.close();
                PreparedStatement preparedStatement;
                if (oldExpense == 0) {
                    preparedStatement = getPrepareStatement("INSERT INTO finance " + "(\"user_id\", \"date\"," + " \"expense\", \"expense_category_id\")" + "VALUES (?, ?, ?, ?)");
                    preparedStatement.setInt(1, currentUser.getUserID());
                    preparedStatement.setString(2, todayDate);
                    preparedStatement.setDouble(3, expense + oldExpense);
                    preparedStatement.setDouble(4, category);

                } else {
                    preparedStatement = getPrepareStatement("UPDATE finance " + "SET \"expense\"=?" + "WHERE \"user_id\"='" + currentUser.getUserID() + "' AND \"expense_category_id\"='" + category + "'");
                    preparedStatement.setDouble(1, expense + oldExpense);

                }
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }

    public double viewExpense(String date) {
        double expense = 0;
        try {
        if (currentUser.isLogin()) {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT \"expense\" FROM finance" + " WHERE \"date\"==\"" + date + "\" AND \"user_id\"==\"" + currentUser.getUserID() + "\"");
            while (resultSet.next()) {
                expense += resultSet.getDouble(1);
            }

            statement.close();
        }
        } catch (SQLException e) {
            // ignore
        }
        return expense;
    }

    public double viewExpenseByCategory(String date, int category) {
        double expense = 0;
        try {
            if (currentUser.isLogin()) {
                Statement statement = getStatement();
                ResultSet resultSet = statement.executeQuery("SELECT \"expense\" FROM finance" + " WHERE \"date\"==\"" + date + "\" AND \"user_id\"==\"" + currentUser.getUserID() + "\" AND \"expense_category_id\"==\"" + category + "\"");
                expense = resultSet.getDouble(1);

                statement.close();
            }
        } catch (SQLException e) {
            // ignore
        }
        return expense;
    }

    public double viewTotalExpenseInCategory(int category) {
        double expense = 0;
        try {
            if (currentUser.isLogin()) {
                Statement statement = getStatement();
                ResultSet resultSet = statement.executeQuery("SELECT \"expense\" FROM finance" + " WHERE \"user_id\"==\"" + currentUser.getUserID() + "\" AND \"expense_category_id\"==\"" + category + "\"");
                expense = resultSet.getDouble(1);

                statement.close();
            }
        } catch (SQLException e) {
            // ignore
        }
        return expense;
    }

}
