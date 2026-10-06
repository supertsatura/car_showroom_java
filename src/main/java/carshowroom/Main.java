package carshowroom;

import carshowroom.repository.BuyerRepository;
import carshowroom.repository.CarRepository;
import carshowroom.repository.OrderRepository;
import carshowroom.service.BuyerService;
import carshowroom.service.CarService;
import carshowroom.service.OrderService;
import carshowroom.ui.Menu;
import carshowroom.util.DatabaseManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        Scanner scanner = new Scanner(System.in);

        try {
            dbManager.getConnection().close();
            System.out.println("Подключение к базе данных установлено.\n");

            BuyerRepository buyerRepo = new BuyerRepository(dbManager);
            CarRepository carRepo = new CarRepository(dbManager);
            OrderRepository orderRepo = new OrderRepository(dbManager);

            BuyerService buyerService = new BuyerService(buyerRepo);
            CarService carService = new CarService(carRepo);
            OrderService orderService = new OrderService(orderRepo);

            Menu menu = new Menu(buyerService, carService, orderService, scanner);
            menu.showMainMenu();

        } catch (Exception e) {
            System.err.println(" Ошибка подключения к базе данных: " + e.getMessage());
            System.err.println("  Убедитесь, что PostgreSQL запущен и база данных создана.");
        } finally {
            scanner.close();
        }
    }
}