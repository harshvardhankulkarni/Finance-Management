package com.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Category extends Database {

    public Category() {
        try {
            Statement statement = getStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS \"expense_types\" (" + "\t\"catagary_id\"\tINTEGER," + "\t\"category\"\tTEXT NOT NULL UNIQUE," + "\tPRIMARY KEY(\"catagary_id\" AUTOINCREMENT)" + ")");
            statement.close();

            boolean dataExist = true;
            Statement statement2 = getStatement();
            ResultSet resultSet = statement2.executeQuery("SELECT \"category\" FROM expense_types");
            if (resultSet.getInt(1) == 0) {
                dataExist = false;
            }
            statement2.close();
            String[] categories = new String[]{"Personal expense", "Shopping", "Food and dinning", "Travelling", "Entrainment", "Medical", "Education", "Bill and Utilities", "Investment", "Taxes", "Insurance", "Other"};
            if (!dataExist) {
                PreparedStatement preparedStatement = getPrepareStatement("INSERT INTO expense_types " + "(\"category\")" + "VALUES (?)");
                for (String category : categories) {
                    preparedStatement.setString(1, category);
                }
                preparedStatement.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }

    public ArrayList<String> getCategories() {
        ArrayList<String> categories = new ArrayList<>();
        try {
            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT \"category\" FROM expense_types");
            while (resultSet.next()) {
                categories.add(resultSet.getString(1));
            }
            statement.close();
        } catch (SQLException e) {
            // ignore
        }
        return categories;
    }
}
