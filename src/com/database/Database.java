package com.database;

import java.sql.*;

class Database {
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:financeDB.db");
    }

    protected Statement getStatement() throws SQLException {
        return getConnection().createStatement();
    }

    protected PreparedStatement getPrepareStatement(String query) throws SQLException {
        return getConnection().prepareStatement(query);
    }
}
