package carshowroom.util;

import carshowroom.exception.DatabaseException;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private final Dotenv dotenv;

    public DatabaseManager() {
        this.dotenv = Dotenv.configure().directory("./").load();
    }

    public Connection getConnection() throws DatabaseException {

        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подключения к БД: " + e.getMessage(), e);
        }
    }
}