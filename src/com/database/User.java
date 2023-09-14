package com.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User extends Database {
    private int userID;
    private String firstName;
    private String lastName;
    private int userAge;
    private String phoneNumber;
    private String userName;
    private String userPassword;
    private boolean loggedIn = false;

    public User() {
        try {
            Statement statement = getStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS \"user\" (" + "\t\"user_id\"\tINTEGER," + "\t\"first_name\"\tTEXT NOT NULL," + "\t\"last_name\"\tTEXT NOT NULL," + "\t\"username\"\tTEXT NOT NULL UNIQUE," + "\t\"user_age\"\tINTEGER NOT NULL," + "\t\"user_phone\"\tTEXT UNIQUE," + "\t\"password\"\tTEXT NOT NULL," + "\tPRIMARY KEY(\"user_id\" AUTOINCREMENT)" + ")");
            statement.close();
        } catch (SQLException e) {
            // ignore
        }
    }

    public void login(String user_name, String password) throws SQLException {
        Statement statement = getStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE \"username\"==\"" + user_name + "\"");
        if (resultSet.getInt(1) != 0) {
            if (password.equals(resultSet.getString(7))) {
                userID = resultSet.getInt(1);
                firstName = resultSet.getString(2);
                lastName = resultSet.getString(3);
                userName = resultSet.getString(4);
                userAge = resultSet.getInt(5);
                phoneNumber = resultSet.getString(6);
                userPassword = resultSet.getString(7);

                loggedIn = true;
            } else {
                throw new SQLException("Password does not match");
            }
        } else {
            throw new SQLException("User not exist");
        }

        statement.close();
    }

    public void logout() {
        userID = 0;
        firstName = null;
        lastName = null;
        userName = null;
        userAge = 0;
        phoneNumber = null;
        userPassword = null;

        loggedIn = false;
    }

    public void signup(String first_name, String last_name, int age, String phone_number, String username, String password) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("INSERT INTO user " + "(\"first_name\", \"last_name\", \"username\"," + " \"user_age\", \"user_phone\", \"password\")" + "VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, first_name);
        preparedStatement.setString(2, last_name);
        preparedStatement.setString(3, username);
        preparedStatement.setInt(4, age);
        preparedStatement.setString(5, phone_number);
        preparedStatement.setString(6, password);

        preparedStatement.executeUpdate();

        login(username, password);

        preparedStatement.close();
    }

    public void updateFirstName(String first_name) throws SQLException {
        if (loggedIn) {
            PreparedStatement preparedStatement = getPrepareStatement("UPDATE user " + "SET \"first_name\"=? WHERE \"user_id\"='" + userID + "' ");
            preparedStatement.setString(1, first_name);

            preparedStatement.executeUpdate();

            firstName = first_name;

            preparedStatement.close();
        } else {
            throw new SQLException("User not Login yet");
        }
    }

    public void updateLastName(String last_name) throws SQLException {
        if (loggedIn) {
            PreparedStatement preparedStatement = getPrepareStatement("UPDATE user " + "SET \"last_name\"=? WHERE \"user_id\"='" + userID + "' ");
            preparedStatement.setString(1, last_name);

            preparedStatement.executeUpdate();

            lastName = last_name;

            preparedStatement.close();
        } else {
            throw new SQLException("User not Login yet");
        }
    }

    public void updateAge(int age) throws SQLException {
        if (loggedIn) {
            PreparedStatement preparedStatement = getPrepareStatement("UPDATE user " + "SET \"user_age\"=? WHERE \"user_id\"='" + userID + "' ");
            preparedStatement.setInt(1, age);

            preparedStatement.executeUpdate();

            userAge = age;

            preparedStatement.close();
        } else {
            throw new SQLException("User not Login yet");
        }
    }

    public void updatePhoneNumber(String phone_number) throws SQLException {
        if (loggedIn) {
            PreparedStatement preparedStatement = getPrepareStatement("UPDATE user " + "SET \"user_phone\"=? WHERE \"user_id\"='" + userID + "' ");
            preparedStatement.setString(1, phone_number);

            preparedStatement.executeUpdate();

            phoneNumber = phone_number;

            preparedStatement.close();
        } else {
            throw new SQLException("User not Login yet");
        }
    }

    public void updateUserName(String username) throws SQLException {
        if (loggedIn) {
            PreparedStatement preparedStatement = getPrepareStatement("UPDATE user " + "SET \"username\"=? WHERE \"user_id\"='" + userID + "' ");
            preparedStatement.setString(1, username);

            preparedStatement.executeUpdate();

            userName = username;

            preparedStatement.close();
        } else {
            throw new SQLException("User not Login yet");
        }
    }

    public void updatePassword(String oldPassword, String newPassword) throws SQLException {
        if (loggedIn) {
            if (oldPassword.equals(newPassword)) {
                PreparedStatement preparedStatement = getPrepareStatement("UPDATE user " + "SET \"password\"=? WHERE \"user_id\"='" + userID + "' ");
                preparedStatement.setString(1, newPassword);

                preparedStatement.executeUpdate();

                userPassword = newPassword;

                preparedStatement.close();
            } else {
                throw new SQLException("Password Not match");
            }
        } else {
            throw new SQLException("User not Login yet");
        }
    }

    public boolean isLogin() {
        return loggedIn;
    }

    public int getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getUserAge() {
        return userAge;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return userName;
    }
}
