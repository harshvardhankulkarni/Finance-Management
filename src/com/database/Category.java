package com.database;

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
