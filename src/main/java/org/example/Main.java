package org.example;

import org.example.auth.AccountManager;
import org.example.database.DatabaseConnection;

import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.connect(Path.of("database.db"));

        AccountManager accountManager = new AccountManager(dbConnection);

        accountManager.init();

        accountManager.register("test", "12345");
        var temp = accountManager.getAccount("test");
        System.out.println(temp);

        var bool = accountManager.authenticate("test", "12345");
        System.out.println(bool);

        dbConnection.disconnect();
    }
}