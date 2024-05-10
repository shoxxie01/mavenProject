package org.example.database;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private java.sql.Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connect(Path path) {
        try {
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", path));
            System.out.println("connected\n");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void disconnect(){

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            System.out.println("Disconnected\n");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
