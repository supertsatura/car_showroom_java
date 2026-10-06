package carshowroom.util;

import carshowroom.exception.DatabaseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/car_showroom_java";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public Connection getConnection() throws DatabaseException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подключения к БД: " + e.getMessage(), e);
        }
    }
}