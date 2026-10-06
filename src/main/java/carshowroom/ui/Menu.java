package carshowroom.ui;

import carshowroom.model.Buyer;
import carshowroom.model.Car;
import carshowroom.model.OrderView;
import carshowroom.model.enums.CarBodyType;
import carshowroom.model.enums.CarCondition;
import carshowroom.service.BuyerService;
import carshowroom.service.CarService;
import carshowroom.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final BuyerService buyerService;
    private final CarService carService;
    private final OrderService orderService;
    private final Scanner scanner;

    public Menu(BuyerService buyerService, CarService carService, OrderService orderService, Scanner scanner) {
        this.buyerService = buyerService;
        this.carService = carService;
        this.orderService = orderService;
        this.scanner = scanner;
    }

    public void showMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("================================================================================");
            System.out.println("                              Автосалон 'Алания авто'");
            System.out.println("================================================================================");
            System.out.println("  1. Покупатели");
            System.out.println("  2. Автомобили");
            System.out.println("  3. Заказы");
            System.out.println("  4. Фильтрация");
            System.out.println("  5. Статистика");
            System.out.println("  6. Экспорт данных");
            System.out.println("  0. Выход");
            System.out.println("================================================================================");
            System.out.print("Выберите действие: ");

            String input = scanner.nextLine().trim();
            try {
                switch (input) {
                    case "1" -> showBuyersMenu();
                    case "2" -> showCarsMenu();
                    case "3" -> showOrdersMenu();
                    case "4" -> showFilterMenu();
                    case "5" -> showStatistics();
                    case "6" -> System.out.println("  [Пока что не реализовано]");
                    case "0" -> {
                        running = false;
                        System.out.println("Выход из программы...");
                    }
                    default -> System.out.println("   Неверный ввод. Выберите число от 0 до 7.");
                }
            } catch (Exception e) {
                System.out.println("   Ошибка: " + e.getMessage());
            }
        }
    }

    // 1. Покупатели
    private void showBuyersMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Покупатели ---");
            System.out.println("  1. Все покупатели");
            System.out.println("  2. Найти по ID");
            System.out.println("  3. Найти по предпочитаемому бренду авто");
            System.out.println("  4. Добавить покупателя");
            System.out.println("  5. Изменить покупателя");
            System.out.println("  6. Удалить покупателя");
            System.out.println("  0. Назад в главное меню");
            System.out.print("Выберите действие: ");

            String input = scanner.nextLine().trim();
            try {
                switch (input) {
                    case "1" -> printAllBuyers();
                    case "2" -> findBuyerById();
                    case "3" -> searchBuyersByBrand();
                    case "4" -> createBuyer();
                    case "5" -> updateBuyer();
                    case "6" -> deleteBuyer();
                    case "0" -> back = true;
                    default -> System.out.println("  Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println("  Ошибка: " + e.getMessage());
            }
        }
    }

    private void printAllBuyers() {
        System.out.println("\n--- Список покупателей ---");
        List<Buyer> buyers = buyerService.getAllBuyers();
        if (buyers.isEmpty()) {
            System.out.println("  Список покупателей пуст.");
            return;
        }
        System.out.printf("  %-5s %-20s %-20s %-20s %-10s %-15s %-15s%n",
                "ID", "Фамилия", "Имя", "Отчество", "Телефон", "Предп. бренд", "Предп. марка");
        System.out.println("  ------------------------------------------------------------------------------------------------------------");
        for (Buyer b : buyers) {
            System.out.printf("  %-5d %-20s %-20s %-20s %-10s %-15s %-15s%n",
                    b.getBuyerId(),
                    b.getBuyerSurname(),
                    b.getBuyerName(),
                    b.getBuyerPatronymic() != null ? b.getBuyerPatronymic() : "-",
                    b.getBuyerTel(),
                    b.getBuyerPreferredBrand() != null ? b.getBuyerPreferredBrand() : "-",
                    b.getBuyerPreferredModel() != null ? b.getBuyerPreferredModel() : "-");
        }
    }

    private void findBuyerById() {
        System.out.print("  Введите ID покупателя: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        buyerService.getBuyerById(id)
                .ifPresentOrElse(
                        b -> System.out.println("  Найден: " + b),
                        () -> System.out.println("  Покупатель с ID " + id + " не найден.")
                );
    }

    private void searchBuyersByBrand() {
        System.out.print("  Введите предпочитаемый бренд: ");
        String brand = scanner.nextLine().trim();
        List<Buyer> buyers = buyerService.getBuyersByPreferredBrand(brand);
        if (buyers.isEmpty()) {
            System.out.println("  Покупатели с предпочитаемым брендом '" + brand + "' не найдены.");
        } else {
            System.out.println("  Найдено покупателей: " + buyers.size());
            for (Buyer b : buyers) {
                System.out.println("  - " + b);
            }
        }
    }

    private void createBuyer() {
        System.out.println("\n--- Добавление покупателя ---");
        Buyer b = new Buyer();
        b.setBuyerSurname(readRequiredString("  Фамилия: "));
        b.setBuyerName(readRequiredString("  Имя: "));
        System.out.print("  Отчество (Enter — пропустить): ");
        String p = scanner.nextLine().trim();
        b.setBuyerPatronymic(p.isEmpty() ? null : p);
        b.setBuyerTel(readPhone());
        System.out.print("  Предпочитаемый бренд авто (Enter — пропустить): ");
        String pref_brand = scanner.nextLine().trim();
        b.setBuyerPreferredBrand(pref_brand.isEmpty() ? null : pref_brand);
        System.out.print("  Предпочитаемая модель авто (Enter — пропустить): ");
        String pref_model = scanner.nextLine().trim();
        b.setBuyerPreferredModel(pref_model.isEmpty() ? null : pref_model);
        buyerService.createBuyer(b);
        System.out.println("  Покупатель добавлен. ID: " + b.getBuyerId());
    }

    private void updateBuyer() {
        System.out.print("  Введите ID покупателя для изменения: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        buyerService.getBuyerById(id).ifPresentOrElse(b -> {
            System.out.println("  Текущие данные: " + b);
            b.setBuyerSurname(readRequiredString("  Новая фамилия [" + b.getBuyerSurname() + "]: ", b.getBuyerSurname()));
            b.setBuyerName(readRequiredString("  Новое имя [" + b.getBuyerName() + "]: ", b.getBuyerName()));
            System.out.print("  Новое отчество [" + (b.getBuyerPatronymic() != null ? b.getBuyerPatronymic() : "-") + "]: ");
            String p = scanner.nextLine().trim();
            if (!p.isEmpty()) b.setBuyerPatronymic(p);
            b.setBuyerTel(readPhone());
            buyerService.updateBuyer(b);
            System.out.println("  Покупатель обновлён.");
        }, () -> System.out.println("  Покупатель с ID " + id + " не найден."));
    }

    private void deleteBuyer() {
        System.out.print("  Введите ID покупателя для удаления: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        try {
            buyerService.deleteBuyer(id);
            System.out.println("  Покупатель удалён.");
        } catch (Exception e) {
            System.out.println("  Х " + e.getMessage());
        }
    }

    // 2. Автомобили
    private void showCarsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Автомобили ---");
            System.out.println("  1. Все автомобили");
            System.out.println("  2. Найти по ID");
            System.out.println("  3. Найти по бренду");
            System.out.println("  4. Найти по состоянию");
            System.out.println("  5. Найти по типу кузова");
            System.out.println("  6. Добавить автомобиль");
            System.out.println("  7. Изменить автомобиль");
            System.out.println("  8. Удалить автомобиль");
            System.out.println("  0. Назад в главное меню");
            System.out.print("Выберите действие: ");

            String input = scanner.nextLine().trim();
            try {
                switch (input) {
                    case "1" -> printAllCars();
                    case "2" -> findCarById();
                    case "3" -> searchCarsByBrand();
                    case "4" -> searchCarsByCondition();
                    case "5" -> searchCarsByBodyType();
                    case "6" -> createCar();
                    case "7" -> updateCar();
                    case "8" -> deleteCar();
                    case "0" -> back = true;
                    default -> System.out.println("   Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println("  Ошибка: " + e.getMessage());
            }
        }
    }

    private void printAllCars() {
        System.out.println("\n--- Список автомобилей ---");
        List<Car> cars = carService.getAllCars();
        if (cars.isEmpty()) {
            System.out.println("  Список автомобилей пуст.");
            return;
        }
        System.out.printf("  %-5s %-12s %-12s %-12s %-10s %-8s %-12s %-10s %-8s %-12s %-10s %-8s %-6s %-12s%n",
                "ID", "Бренд", "Модель", "Год", "Состояние", "Пробег", "Кузов", "Привод", "КПП",
                "Комплект.", "Цвет", "Объём", "Л.С.", "Цена");
        System.out.println("  -----------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Car c : cars) {
            System.out.printf("  %-5d %-12s %-12s %-12s %-10s %-8d %-12s %-10s %-8s %-12s %-10s %-8s %-6d %-12s%n",
                    c.getCarId(), c.getCarBrand(), c.getCarModel(),
                    c.getCarReleaseDate() != null ? c.getCarReleaseDate().toString() : "-",
                    c.getCarCondition().getValue(), c.getCarMileage(),
                    c.getCarBodyType().getValue(), c.getCarWheelDrive().getValue(),
                    c.getCarGearbox().getValue(), c.getCarCompletion().getValue(),
                    c.getCarColor().getValue(), c.getCarEngineCapacity(),
                    c.getCarHorsePower(), c.getCarPrice());
        }
    }

    private void findCarById() {
        System.out.print("  Введите ID автомобиля: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        carService.getCarById(id)
                .ifPresentOrElse(
                        c -> System.out.println("  Найден: " + c),
                        () -> System.out.println("  Автомобиль с ID " + id + " не найден.")
                );
    }

    private void searchCarsByBrand() {
        System.out.print("  Введите бренд: ");
        String brand = scanner.nextLine().trim();
        List<Car> cars = carService.getCarsByBrand(brand);
        if (cars.isEmpty()) {
            System.out.println("  Автомобили бренда '" + brand + "' не найдены.");
        } else {
            System.out.println("  Найдено автомобилей: " + cars.size());
            for (Car c : cars) {
                System.out.println("  - " + c);
            }
        }
    }

    private void searchCarsByCondition() {
        CarCondition condition = readEnum("  Введите состояние (Новый/Б/У): ", CarCondition.class);
        List<Car> cars = carService.getCarsByCondition(condition);
        if (cars.isEmpty()) {
            System.out.println("  Автомобили с состоянием '" + condition.getValue() + "' не найдены.");
        } else {
            System.out.println("  Найдено автомобилей: " + cars.size());
            for (Car c : cars) {
                System.out.println("  - " + c);
            }
        }
    }

    private void searchCarsByBodyType() {
        CarBodyType bodyType = readEnum("  Введите тип кузова: ", CarBodyType.class);
        List<Car> cars = carService.getCarsByBodyType(bodyType);
        if (cars.isEmpty()) {
            System.out.println("  Автомобили с типом кузова '" + bodyType.getValue() + "' не найдены.");
        } else {
            System.out.println("  Найдено автомобилей: " + cars.size());
            for (Car c : cars) {
                System.out.println("  - " + c);
            }
        }
    }

    private void createCar() {
        System.out.println("\n--- Добавление автомобиля ---");
        Car c = new Car();
        c.setCarBrand(readRequiredString("  Бренд: "));
        c.setCarModel(readRequiredString("  Модель: "));
        c.setCarReleaseDate(readDate());
        c.setCarCondition(readEnum("  Состояние (Новый/Б/У): ", carshowroom.model.enums.CarCondition.class));
        c.setCarMileage(readPositiveInt("  Пробег (км): "));
        c.setCarBodyType(readEnum("  Тип кузова (Хэтчбек/Седан/Купе/Кабриолет/Универсал/Внедорожник/Кроссовер/Вэн): ",
                carshowroom.model.enums.CarBodyType.class));
        c.setCarWheelDrive(readEnum("  Привод (Передний/Задний/Полный): ", carshowroom.model.enums.CarWheelDrive.class));
        c.setCarGearbox(readEnum("  КПП (АКПП/МКПП): ", carshowroom.model.enums.CarGearbox.class));
        c.setCarCompletion(readEnum("  Комплектация (Базовая/Средняя/Максимальная): ", carshowroom.model.enums.CarCompletion.class));
        c.setCarColor(readEnum("  Цвет (Белый/Серый/Черный/Синий/Красный/Зеленый/Желтый/Оранжевый/Фиолетовый): ",
                carshowroom.model.enums.CarColor.class));
        c.setCarEngineCapacity(readBigDecimal("  Объём двигателя (л): "));
        c.setCarHorsePower(readPositiveInt("  Мощность (л.с.): "));
        c.setCarPrice(readBigDecimal("  Цена (руб.): "));
        carService.createCar(c);
        System.out.println("  Автомобиль добавлен. ID: " + c.getCarId());
    }

    private void updateCar() {
        System.out.print("  Введите ID автомобиля для изменения: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        carService.getCarById(id).ifPresentOrElse(c -> {
            System.out.println("  Текущие данные: " + c);
            c.setCarBrand(readRequiredString("  Новый бренд [" + c.getCarBrand() + "]: ", c.getCarBrand()));
            c.setCarModel(readRequiredString("  Новая модель [" + c.getCarModel() + "]: ", c.getCarModel()));
            c.setCarReleaseDate(readDate());
            c.setCarCondition(readEnum("  Состояние: ", carshowroom.model.enums.CarCondition.class));
            c.setCarMileage(readNonNegativeInt("  Пробег: "));
            c.setCarBodyType(readEnum("  Тип кузова: ", carshowroom.model.enums.CarBodyType.class));
            c.setCarWheelDrive(readEnum("  Привод: ", carshowroom.model.enums.CarWheelDrive.class));
            c.setCarGearbox(readEnum("  КПП: ", carshowroom.model.enums.CarGearbox.class));
            c.setCarCompletion(readEnum("  Комплектация: ", carshowroom.model.enums.CarCompletion.class));
            c.setCarColor(readEnum("  Цвет: ", carshowroom.model.enums.CarColor.class));
            c.setCarEngineCapacity(readBigDecimal("  Объём двигателя: "));
            c.setCarHorsePower(readNonNegativeInt("  Мощность: "));
            c.setCarPrice(readBigDecimal("  Цена: "));
            carService.updateCar(c);
            System.out.println("  Автомобиль обновлён.");
        }, () -> System.out.println("  Автомобиль с ID " + id + " не найден."));
    }

    private void deleteCar() {
        System.out.print("  Введите ID автомобиля для удаления: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        try {
            carService.deleteCar(id);
            System.out.println("  Автомобиль удалён.");
        } catch (Exception e) {
            System.out.println("  Х " + e.getMessage());
        }
    }

    // 3. Заказы
    private void showOrdersMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Заказы ---");
            System.out.println("  1. Все заказы");
            System.out.println("  2. Найти по ID");
            System.out.println("  3. Добавить заказ");
            System.out.println("  4. Изменить заказ");
            System.out.println("  5. Удалить заказ");
            System.out.println("  0. Назад в главное меню");
            System.out.print("Выберите действие: ");

            String input = scanner.nextLine().trim();
            try {
                switch (input) {
                    case "1" -> printAllOrders();
                    case "2" -> findOrderById();
                    case "3" -> createOrder();
                    case "4" -> updateOrder();
                    case "5" -> deleteOrder();
                    case "0" -> back = true;
                    default -> System.out.println("   Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println("  Ошибка: " + e.getMessage());
            }
        }
    }

    private void printAllOrders() {
        System.out.println("\n--- Список заказов ---");
        List<OrderView> orders = orderService.getAllOrdersWithDetails();
        if (orders.isEmpty()) {
            System.out.println("  Список заказов пуст.");
            return;
        }
        System.out.printf("  %-5s %-12s %-30s %-12s %-8s %-20s %-12s %-12s%n",
                "ID", "Статус", "Покупатель", "Телефон", "ID авто", "Автомобиль", "Дата", "Сумма");
        System.out.println("  ----------------------------------------------------------------------------------------------------------------------------");
        for (OrderView o : orders) {
            System.out.printf("  %-5d %-12s %-30s %-12s %-8d %-20s %-12s %-12s%n",
                    o.getOrderId(),
                    o.getOrderStatus().getValue(),
                    o.getBuyerFullName(),
                    o.getBuyerTel(),
                    o.getCarId(),
                    o.getCarFullName(),
                    o.getOrderDate().toString(),
                    o.getOrderSum());
        }
    }

    private void findOrderById() {
        System.out.print("  Введите ID заказа: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        orderService.getOrderById(id)
                .ifPresentOrElse(
                        o -> System.out.println("  Найден заказ ID=" + o.getOrderId() +
                                ", покупатель ID=" + o.getBuyerId() +
                                ", автомобиль ID=" + o.getCarId() +
                                ", статус=" + o.getOrderStatus().getValue()),
                        () -> System.out.println("  Заказ с ID " + id + " не найден.")
                );
    }

    private void createOrder() {
        System.out.println("\n--- Добавление заказа ---");
        carshowroom.model.Order o = new carshowroom.model.Order();
        o.setBuyerId(readPositiveInt("  ID покупателя: "));
        o.setCarId(readPositiveInt("  ID автомобиля: "));
        o.setOrderStatus(readEnum("  Статус (Ожидает/Подтверждён/Завершён/Отменён): ",
                carshowroom.model.enums.OrderStatus.class));
        o.setOrderDate(readDate());
        o.setOrderSum(readBigDecimal("  Сумма заказа (руб.): "));
        orderService.createOrder(o);
        System.out.println("  Заказ добавлен. ID: " + o.getOrderId());
    }

    private void updateOrder() {
        System.out.print("  Введите ID заказа для изменения: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        orderService.getOrderById(id).ifPresentOrElse(o -> {
            System.out.println("  Текущий заказ: ID=" + o.getOrderId() + ", статус=" + o.getOrderStatus().getValue());
            o.setBuyerId(readPositiveInt("  ID покупателя: "));
            o.setCarId(readPositiveInt("  ID автомобиля: "));
            o.setOrderStatus(readEnum("  Статус: ", carshowroom.model.enums.OrderStatus.class));
            o.setOrderDate(readDate());
            o.setOrderSum(readBigDecimal("  Сумма: "));
            orderService.updateOrder(o);
            System.out.println("  Заказ обновлён.");
        }, () -> System.out.println("  Заказ с ID " + id + " не найден."));
    }

    private void deleteOrder() {
        System.out.print("  Введите ID заказа для удаления: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        try {
            orderService.deleteOrder(id);
            System.out.println("  Заказ удалён.");
        } catch (Exception e) {
            System.out.println("  Х " + e.getMessage());
        }
    }

    // 4. Фильтрация
    private void showFilterMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Фильтрация ---");
            System.out.println("  1. Фильтрация автомобилей");
            System.out.println("  2. Фильтрация заказов");
            System.out.println("  0. Назад в главное меню");
            System.out.print("Выберите действие: ");

            String input = scanner.nextLine().trim();
            try {
                switch (input) {
                    case "1" -> showCarFilterMenu();
                    case "2" -> showOrderFilterMenu();
                    case "0" -> back = true;
                    default  -> System.out.println("  Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println("  Х Ошибка: " + e.getMessage());
            }
        }
    }

    private void showCarFilterMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Фильтрация автомобилей ---");
            System.out.println("  1. По году выпуска");
            System.out.println("  2. По пробегу");
            System.out.println("  0. Назад");
            System.out.print("Выберите действие: ");

            String input = scanner.nextLine().trim();
            try {
                switch (input) {
                    case "1" -> filterCarsByReleaseDate();
                    case "2" -> filterCarsByMileage();
                    case "0" -> back = true;
                    default  -> System.out.println("   Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println("  Х Ошибка: " + e.getMessage());
            }
        }
    }

    private void filterCarsByReleaseDate() {
        System.out.println("\n--- Автомобили по году выпуска ---");
        List<Car> cars = carService.getCarsSortedByReleaseDateDesc();
        if (cars.isEmpty()) {
            System.out.println("  Список пуст.");
            return;
        }

        System.out.printf("  %-5s %-12s %-12s %-10s %-10s %-12s%n",
                "ID", "Бренд", "Модель", "Год", "Пробег", "Цена");
        System.out.println("  -------------------------------------------------------------");

        for (Car c : cars) {
            System.out.printf("  %-5d %-12s %-12s %-10s %-10d %-12s%n",
                    c.getCarId(),
                    c.getCarBrand(),
                    c.getCarModel(),
                    c.getCarReleaseDate() != null ? c.getCarReleaseDate().toString() : "-",
                    c.getCarMileage(),
                    c.getCarPrice() + " руб.");
        }
    }

    private void filterCarsByMileage() {
        System.out.println("\n--- Автомобили по пробегу ---");
        List<Car> cars = carService.getCarsSortedByMileageDesc();
        if (cars.isEmpty()) {
            System.out.println("  Список пуст.");
            return;
        }

        System.out.printf("  %-5s %-12s %-12s %-10s %-10s %-12s%n",
                "ID", "Бренд", "Модель", "Год", "Пробег", "Цена");
        System.out.println("  -------------------------------------------------------------");

        for (Car c : cars) {
            System.out.printf("  %-5d %-12s %-12s %-10s %-10d %-12s%n",
                    c.getCarId(),
                    c.getCarBrand(),
                    c.getCarModel(),
                    c.getCarReleaseDate() != null ? c.getCarReleaseDate().toString() : "-",
                    c.getCarMileage(),
                    c.getCarPrice() + " руб.");
        }
    }

    private void showOrderFilterMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Фильтрация заказов ---");
            System.out.println("  1. По дате");
            System.out.println("  2. По сумме заказа");
            System.out.println("  0. Назад");
            System.out.print("Выберите действие: ");

            String input = scanner.nextLine().trim();
            try {
                switch (input) {
                    case "1" -> filterOrdersByDate();
                    case "2" -> filterOrdersBySum();
                    case "0" -> back = true;
                    default  -> System.out.println("  Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println("  Х Ошибка: " + e.getMessage());
            }
        }
    }

    private void filterOrdersByDate() {
        System.out.println("\n--- Заказы по дате ---");
        List<OrderView> orders = orderService.getOrdersSortedByDateDesc();
        if (orders.isEmpty()) {
            System.out.println("  Список пуст.");
            return;
        }

        System.out.printf("  %-5s %-12s %-30s %-12s %-8s %-20s %-12s %-12s%n",
                "ID", "Статус", "Покупатель", "Телефон", "ID авто", "Автомобиль", "Дата", "Сумма");
        System.out.println("  ----------------------------------------------------------------------------------------------------------------------------");

        for (OrderView o : orders) {
            System.out.printf("  %-5d %-12s %-30s %-12s %-8d %-20s %-12s %-12s%n",
                    o.getOrderId(),
                    o.getOrderStatus().getValue(),
                    o.getBuyerFullName(),
                    o.getBuyerTel(),
                    o.getCarId(),
                    o.getCarFullName(),
                    o.getOrderDate().toString(),
                    o.getOrderSum());
        }
    }

    private void filterOrdersBySum() {
        System.out.println("\n--- Заказы по сумме ---");
        List<OrderView> orders = orderService.getOrdersSortedBySumDesc();
        if (orders.isEmpty()) {
            System.out.println("  Список пуст.");
            return;
        }

        System.out.printf("  %-5s %-12s %-30s %-12s %-8s %-20s %-12s %-12s%n",
                "ID", "Статус", "Покупатель", "Телефон", "ID авто", "Автомобиль", "Дата", "Сумма");
        System.out.println("  ----------------------------------------------------------------------------------------------------------------------------");

        for (OrderView o : orders) {
            System.out.printf("  %-5d %-12s %-30s %-12s %-8d %-20s %-12s %-12s%n",
                    o.getOrderId(),
                    o.getOrderStatus().getValue(),
                    o.getBuyerFullName(),
                    o.getBuyerTel(),
                    o.getCarId(),
                    o.getCarFullName(),
                    o.getOrderDate().toString(),
                    o.getOrderSum());
        }
    }

    // 5. Статистика
    private void showStatistics() {
        System.out.println("\n--- Статистика ---");
        System.out.println("  1. Количество записей в таблице покупатели: " + buyerService.getBuyersCount());
        System.out.println("  2. Количество записей в таблице автомобили: " + carService.getCarsCount());
        System.out.println("  3. Количество записей в таблице заказы: " + orderService.getOrdersCount());
        System.out.println("  4. Количество ожидаемых заказов: " + orderService.getOrdersCountByStatus(carshowroom.model.enums.OrderStatus.PENDING));
        System.out.println("  5. Количество подтверждённых заказов: " + orderService.getOrdersCountByStatus(carshowroom.model.enums.OrderStatus.CONFIRMED));
        System.out.println("  6. Количество завершённых заказов: " + orderService.getOrdersCountByStatus(carshowroom.model.enums.OrderStatus.COMPLETED));
        System.out.println("  7. Количество отменённых заказов: " + orderService.getOrdersCountByStatus(carshowroom.model.enums.OrderStatus.CANCELLED));
    }

    // 6. Экспорт данных в Excel
//    private void exportData() {
//        System.out.println("\n--- Экспорт данных ---");
//        System.out.print("  Введите путь для сохранения файла (например, data.xlsx): ");
//        String filePath = scanner.nextLine().trim();
//        if (filePath.isEmpty()) {
//            filePath = "autosalon_data.xlsx";
//        }
//        try {
//            ExcelExporter exporter = new ExcelExporter(buyerService, carService, orderService);
//            exporter.exportToExcel(filePath);
//            System.out.println("  Данные экспортированы в файл: " + filePath);
//        } catch (Exception e) {
//            System.out.println("  Х Ошибка экспорта: " + e.getMessage());
//        }
//    }

    // Валидирующие функции
    private String readRequiredString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("  Поле не может быть пустым.");
        }
    }

    private String readRequiredString(String prompt, String defaultValue) {
        System.out.print(prompt);
        String s = scanner.nextLine().trim();
        return s.isEmpty() ? defaultValue : s;
    }

    private String readPhone() {
        while (true) {
            System.out.print("  Телефон (формат XX-XX-XX): ");
            String s = scanner.nextLine().trim();
            if (s.matches("^\\d{2}-\\d{2}-\\d{2}$")) return s;
            System.out.println("  Неверный формат. Пример: 77-77-77");
        }
    }

    private LocalDate readDate() {
        while (true) {
            System.out.print("  Дата (ГГГГ-ММ-ДД): ");
            String s = scanner.nextLine().trim();
            try {
                return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (Exception e) {
                System.out.println("   Неверный формат даты.");
            }
        }
    }

    private int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v > 0) return v;
                System.out.println("  Число должно быть положительным.");
            } catch (NumberFormatException e) {
                System.out.println("  Введено не число.");
            }
        }
    }

    private int readNonNegativeInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v >= 0) return v;
                System.out.println("  Число должно быть положительным.");
            } catch (NumberFormatException e) {
                System.out.println("  Введено не число.");
            }
        }
    }

    private BigDecimal readBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            try {
                return new BigDecimal(s);
            } catch (NumberFormatException e) {
                System.out.println("  Введено не число.");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <E extends Enum<E>> E readEnum(String prompt, Class<E> enumClass) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            for (E e : enumClass.getEnumConstants()) {
                try {
                    String val = (String) e.getClass().getMethod("getValue").invoke(e);
                    if (val.equalsIgnoreCase(s)) return e;
                } catch (Exception ex) {
                    if (e.name().equalsIgnoreCase(s)) return e;
                }
            }
            System.out.println("  Неверное значение. Допустимые: " + Arrays.toString(enumClass.getEnumConstants()));
        }
    }
}