package org.example.auth;

import org.example.database.DatabaseConnection;
import java.nio.file.Path;
import java.sql.*;

public class AccountManager {

    private DatabaseConnection dbConnection;

    public AccountManager(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    public void init(){

        try {
            String createSQLTable = "CREATE TABLE IF NOT EXISTS accounts( "
                    + "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + "username TEXT NOT NULL,"
                    + "password TEXT NOT NULL)";

            dbConnection.connect(Path.of("database.db"));
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(createSQLTable);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean register(String username, String password){
        try {

            Account account = getAccount(username);

            if(account == null) {
                String insertSQL = "INSERT INTO accounts (username, password) VALUES (?, ?)";
                Connection connection = dbConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(insertSQL);
                statement.setString(1, username);
                statement.setString(2, password);
                statement.executeUpdate();
                return true;
            }else{
                return false;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Account getAccount(String username){
        String query = "Select id, username FROM accounts WHERE username=?";
        Connection connection = dbConnection.getConnection();

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();

            if(results.next()){
                int id = results.getInt("id");
                String usernameNew = results.getString("username");
                return new Account(id, usernameNew);
            }
            return null;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean authenticate(String username, String password){
        String query = "SELECT password FROM accounts WHERE username=?";

        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                String passwordStore = resultSet.getString("password");

                if(password.equals(passwordStore)){
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }




}
