package carshowroom;

import carshowroom.repository.BuyerRepository;
import carshowroom.repository.CarRepository;
import carshowroom.repository.OrderRepository;
import carshowroom.service.BuyerService;
import carshowroom.service.CarService;
import carshowroom.service.OrderService;
import carshowroom.ui.Menu;
import carshowroom.util.DatabaseManager;
import carshowroom.util.InputReader;

import java.sql.Connection;


public class Main {

    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        InputReader inputReader = new InputReader();

        try (Connection conn = dbManager.getConnection()) {

            if (conn.isValid(10)) {
                System.out.println("Подключение к базе данных установлено.");

                Menu menu = createMenu(dbManager, inputReader);
                menu.showMainMenu();
            } else {
                System.out.println("База данных не отвечает.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("  Убедитесь, что PostgreSQL запущен и база данных создана.");
        } finally {
            inputReader.close();
        }
    }

    private static Menu createMenu(DatabaseManager dbManager, InputReader inputReader) {
        BuyerRepository buyerRepo = new BuyerRepository(dbManager);
        CarRepository carRepo = new CarRepository(dbManager);
        OrderRepository orderRepo = new OrderRepository(dbManager);

        BuyerService buyerService = new BuyerService(buyerRepo);
        CarService carService = new CarService(carRepo);
        OrderService orderService = new OrderService(orderRepo, buyerRepo, carRepo);

        return new Menu(buyerService, carService, orderService, inputReader);
    }
}