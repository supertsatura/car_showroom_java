package carshowroom.ui;

import carshowroom.model.Buyer;
import carshowroom.model.Car;
import carshowroom.model.Order;
import carshowroom.model.OrderView;
import carshowroom.model.enums.CarBodyType;
import carshowroom.model.enums.CarCondition;
import carshowroom.service.BuyerService;
import carshowroom.service.CarService;
import carshowroom.service.OrderService;
import carshowroom.util.InputReader;

import java.util.List;
import java.util.Optional;


public class Menu {

    private final BuyerService buyerService;
    private final CarService carService;
    private final OrderService orderService;
    private final InputReader inputReader;

    public Menu(BuyerService buyerService, CarService carService, OrderService orderService, InputReader inputReader) {
        this.buyerService = buyerService;
        this.carService = carService;
        this.orderService = orderService;
        this.inputReader = inputReader;
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

            int input = inputReader.readPositiveInt("Выберите действие: ");

            try {
                switch (input) {
                    case 1 -> showBuyersMenu();
                    case 2 -> showCarsMenu();
                    case 3 -> showOrdersMenu();
                    case 4 -> showFilterMenu();
                    case 5 -> showStatistics();
                    case 6 -> System.out.println("  [Пока что не реализовано]");
                    case 0 -> {
                        running = false;
                        System.out.println("Выход из программы...");
                    }
                    default -> System.out.println("   Неверный ввод. Выберите число от 0 до 7.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // =============================================== ПОКУПАТЕЛИ ================================================
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

            int input = inputReader.readPositiveInt("Выберите действие: ");

            try {
                switch (input) {
                    case 1 -> printAllBuyers();
                    case 2 -> findBuyerById();
                    case 3 -> searchBuyersByBrand();
                    case 4 -> createBuyer();
                    case 5 -> updateBuyer();
                    case 6 -> deleteBuyer();
                    case 0 -> back = true;
                    default -> System.out.println("  Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printAllBuyers() {
        System.out.println("--- Список покупателей ---");

        List<Buyer> buyers = buyerService.getAllBuyers();

        if (buyers.isEmpty()) {
            System.out.println("  Список покупателей пуст.");
            return;
        }

        System.out.printf("  %-5s %-20s %-20s %-20s %-15s %-20s %-25s %-20s %-15s %-20s %-20s %-20s %-20s %-20s %-20s %-15s%n",
                "ID", "Фамилия", "Имя", "Отчество", "Телефон", "Марка", "Модель", "Кузов", "Привод", "Трансмиссия", "Состояние", "Комплектация", "Цвет", "Год выпуска", "Пробег", "Бюджет (руб.)");
        System.out.println("  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Buyer b : buyers) {
            System.out.printf("  %-5d %-20s %-20s %-20s %-15s %-20s %-25s %-20s %-15s %-20s %-20s %-20s %-20s %-20s %-20s %-15s%n",
                    b.getBuyerId(),
                    b.getBuyerSurname(),
                    b.getBuyerName(),
                    b.getBuyerPatronymic() != null ? b.getBuyerPatronymic() : "-",
                    b.getBuyerTel(),
                    b.getBuyerPreferredBrand() != null ? b.getBuyerPreferredBrand() : "-",
                    b.getBuyerPreferredModel() != null ? b.getBuyerPreferredModel() : "-",
                    b.getBuyerPreferredBodyType() != null ? b.getBuyerPreferredBodyType().getValue() : "-",
                    b.getBuyerPreferredWheelDrive() != null ? b.getBuyerPreferredWheelDrive().getValue() : "-",
                    b.getBuyerPreferredGearbox() != null ? b.getBuyerPreferredGearbox().getValue() : "-",
                    b.getBuyerPreferredCondition() != null ? b.getBuyerPreferredCondition().getValue() : "-",
                    b.getBuyerPreferredCompletion() != null ? b.getBuyerPreferredCompletion().getValue() : "-",
                    b.getBuyerPreferredColor() != null ? b.getBuyerPreferredColor().getValue() : "-",
                    b.getBuyerPreferredCarReleaseDate() != null ? b.getBuyerPreferredCarReleaseDate().toString() : "-",
                    b.getBuyerPreferredCarMaxMileage() != null ? b.getBuyerPreferredCarMaxMileage().toString() : "-",
                    b.getBuyerPreferredCarMaxPrice() != null ? b.getBuyerPreferredCarMaxPrice().toString() : "-");
        }
    }

    private void findBuyerById() {
        int id = inputReader.readPositiveInt("  Введите ID покупателя: ");
        Optional<Buyer> optBuyer = buyerService.getBuyerById(id);

        if (optBuyer.isEmpty()) {
            System.out.println("  Покупатель с ID " + id + " не найден.");
            return;
        }

        Buyer b = optBuyer.get();

        System.out.println("  Найден покупатель: ");
        System.out.printf("  %-5s %-20s %-20s %-20s %-15s %-20s %-25s %-20s %-15s %-20s %-20s %-20s %-20s %-20s %-20s %-15s%n",
                "ID", "Фамилия", "Имя", "Отчество", "Телефон", "Марка", "Модель", "Кузов", "Привод", "Трансмиссия", "Состояние", "Комплектация", "Цвет", "Год выпуска", "Пробег", "Бюджет (руб.)");
        System.out.println("  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("  %-5d %-20s %-20s %-20s %-15s %-20s %-25s %-20s %-15s %-20s %-20s %-20s %-20s %-20s %-20s %-15s%n",
                b.getBuyerId(),
                b.getBuyerSurname(),
                b.getBuyerName(),
                b.getBuyerPatronymic() != null ? b.getBuyerPatronymic() : "-",
                b.getBuyerTel(),
                b.getBuyerPreferredBrand() != null ? b.getBuyerPreferredBrand() : "-",
                b.getBuyerPreferredModel() != null ? b.getBuyerPreferredModel() : "-",
                b.getBuyerPreferredBodyType() != null ? b.getBuyerPreferredBodyType().getValue() : "-",
                b.getBuyerPreferredWheelDrive() != null ? b.getBuyerPreferredWheelDrive().getValue() : "-",
                b.getBuyerPreferredGearbox() != null ? b.getBuyerPreferredGearbox().getValue() : "-",
                b.getBuyerPreferredCondition() != null ? b.getBuyerPreferredCondition().getValue() : "-",
                b.getBuyerPreferredCompletion() != null ? b.getBuyerPreferredCompletion().getValue() : "-",
                b.getBuyerPreferredColor() != null ? b.getBuyerPreferredColor().getValue() : "-",
                b.getBuyerPreferredCarReleaseDate() != null ? b.getBuyerPreferredCarReleaseDate().toString() : "-",
                b.getBuyerPreferredCarMaxMileage() != null ? b.getBuyerPreferredCarMaxMileage().toString() : "-",
                b.getBuyerPreferredCarMaxPrice() != null ? b.getBuyerPreferredCarMaxPrice().toString() : "-");
    }

    private void searchBuyersByBrand() {
        String brand = inputReader.readRequiredString("  Введите предпочитаемую марку: ");
        List<Buyer> buyers = buyerService.getBuyersByPreferredBrand(brand);

        if (buyers.isEmpty()) {
            System.out.println("  Покупатели с предпочитаемой маркой '" + brand + "' не найдены.");
        } else {
            System.out.println("  Найдено покупателей: " + buyers.size());
            System.out.println();
            System.out.printf("  %-5s %-20s %-20s %-20s %-15s %-20s %-25s %-20s %-15s %-20s %-20s %-20s %-20s %-20s %-20s %-15s%n",
                    "ID", "Фамилия", "Имя", "Отчество", "Телефон", "Марка", "Модель", "Кузов", "Привод", "Трансмиссия", "Состояние", "Комплектация", "Цвет", "Год выпуска", "Пробег", "Бюджет (руб.)");
            System.out.println("  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for (Buyer b : buyers) {
                System.out.printf("  %-5d %-20s %-20s %-20s %-15s %-20s %-25s %-20s %-15s %-20s %-20s %-20s %-20s %-20s %-20s %-15s%n",
                        b.getBuyerId(),
                        b.getBuyerSurname(),
                        b.getBuyerName(),
                        b.getBuyerPatronymic() != null ? b.getBuyerPatronymic() : "-",
                        b.getBuyerTel(),
                        b.getBuyerPreferredBrand() != null ? b.getBuyerPreferredBrand() : "-",
                        b.getBuyerPreferredModel() != null ? b.getBuyerPreferredModel() : "-",
                        b.getBuyerPreferredBodyType() != null ? b.getBuyerPreferredBodyType().getValue() : "-",
                        b.getBuyerPreferredWheelDrive() != null ? b.getBuyerPreferredWheelDrive().getValue() : "-",
                        b.getBuyerPreferredGearbox() != null ? b.getBuyerPreferredGearbox().getValue() : "-",
                        b.getBuyerPreferredCondition() != null ? b.getBuyerPreferredCondition().getValue() : "-",
                        b.getBuyerPreferredCompletion() != null ? b.getBuyerPreferredCompletion().getValue() : "-",
                        b.getBuyerPreferredColor() != null ? b.getBuyerPreferredColor().getValue() : "-",
                        b.getBuyerPreferredCarReleaseDate() != null ? b.getBuyerPreferredCarReleaseDate().toString() : "-",
                        b.getBuyerPreferredCarMaxMileage() != null ? b.getBuyerPreferredCarMaxMileage().toString() : "-",
                        b.getBuyerPreferredCarMaxPrice() != null ? b.getBuyerPreferredCarMaxPrice().toString() : "-");
            }
        }
    }

    private void createBuyer() {
        System.out.println("--- Добавление покупателя ---");

        Buyer b = new Buyer();
        b.setBuyerSurname(inputReader.readRequiredString("  Фамилия: "));
        b.setBuyerName(inputReader.readRequiredString("  Имя: "));
        b.setBuyerPatronymic(inputReader.readNonRequiredString("  Отчество (Enter — пропустить): "));
        b.setBuyerTel(inputReader.readRequiredPhone());
        System.out.println("  Введите предпочитаемые характеристики  ");
        b.setBuyerPreferredBrand(inputReader.readNonRequiredString("  Марка (Enter — пропустить): "));
        b.setBuyerPreferredModel(inputReader.readNonRequiredString("  Модель (Enter — пропустить): "));
        b.setBuyerPreferredBodyType(inputReader.readEnum("  Тип Кузова (Хэтчбек/Седан/Купе/Кабриолет/Универсал/Внедорожник/Кроссовер/Вэн) (Enter — пропустить): ", carshowroom.model.enums.CarBodyType.class, false));
        b.setBuyerPreferredWheelDrive(inputReader.readEnum("  Привод (Передний/Задний/Полный) (Enter — пропустить): ", carshowroom.model.enums.CarWheelDrive.class, false));
        b.setBuyerPreferredGearbox(inputReader.readEnum("  Трансмиссия (АКПП/МКПП) (Enter — пропустить): ", carshowroom.model.enums.CarGearbox.class, false));
        b.setBuyerPreferredCondition(inputReader.readEnum("  Состояние (Новый/Б/У) (Enter — пропустить): ", carshowroom.model.enums.CarCondition.class, false));
        b.setBuyerPreferredCompletion(inputReader.readEnum("  Комплектация (Базовая/Средняя/Максимальная) (Enter — пропустить): ", carshowroom.model.enums.CarCompletion.class, false));
        b.setBuyerPreferredColor(inputReader.readEnum("  Цвет (Белый/Серый/Черный/Синий/Красный/Зеленый/Желтый/Оранжевый/Фиолетовый) (Enter — пропустить): ", carshowroom.model.enums.CarColor.class, false));
        b.setBuyerPreferredCarReleaseDate(inputReader.readNonRequiredDate());
        b.setBuyerPreferredCarMaxMileage(inputReader.readOptionalPositiveInt());
        b.setBuyerPreferredCarMaxPrice(inputReader.readBigDecimal("  Цена в рублях (Enter — пропустить): "));
        buyerService.createBuyer(b);

        System.out.println("  Покупатель добавлен. ID: " + b.getBuyerId());
    }

    private void updateBuyer() {
        int id = inputReader.readPositiveInt("  Введите ID покупателя для изменения: ");

        buyerService.getBuyerById(id).ifPresentOrElse(b -> {
            b.setBuyerSurname(inputReader.readNonRequiredString("  Новая фамилия [" + b.getBuyerSurname() + "]: ", b.getBuyerSurname()));
            b.setBuyerName(inputReader.readNonRequiredString("  Новое имя [" + b.getBuyerName() + "]: ", b.getBuyerName()));
            b.setBuyerPatronymic(inputReader.readNonRequiredString("  Новое отчество [" + b.getBuyerPatronymic() + "]: ", b.getBuyerPatronymic()));
            b.setBuyerTel(inputReader.readNonRequiredPhone("  Новый телефон [" + b.getBuyerTel() + "]: ", b.getBuyerTel()));
            b.setBuyerPreferredBrand(inputReader.readNonRequiredString("  Новая марка [" + b.getBuyerPreferredBrand() + "]: ", b.getBuyerPreferredBrand()));
            b.setBuyerPreferredModel(inputReader.readNonRequiredString("  Новая модель [" + b.getBuyerPreferredModel() + "]: ", b.getBuyerPreferredModel()));
            b.setBuyerPreferredBodyType(inputReader.readEnum("  Новый тип кузова (Хэтчбек/Седан/Купе/Кабриолет/Универсал/Внедорожник/Кроссовер/Вэн) [" + b.getBuyerPreferredBodyType() + "]: ", carshowroom.model.enums.CarBodyType.class, false));
            b.setBuyerPreferredWheelDrive(inputReader.readEnum("  Новый привод (Передний/Задний/Полный) [" + b.getBuyerPreferredWheelDrive() + "]: ", carshowroom.model.enums.CarWheelDrive.class, false));
            b.setBuyerPreferredGearbox(inputReader.readEnum("  Новая трансмиссия (АКПП/МКПП) [" + b.getBuyerPreferredGearbox() + "]: ", carshowroom.model.enums.CarGearbox.class, false));
            b.setBuyerPreferredCondition(inputReader.readEnum("  Новое состояние (Новый/Б/У) [" + b.getBuyerPreferredCondition() + "]: ", carshowroom.model.enums.CarCondition.class, false));
            b.setBuyerPreferredCompletion(inputReader.readEnum("  Новая комплектация (Базовая/Средняя/Максимальная) [" + b.getBuyerPreferredCompletion() + "]: ", carshowroom.model.enums.CarCompletion.class, false));
            b.setBuyerPreferredColor(inputReader.readEnum("  Новый цвет (Белый/Серый/Черный/Синий/Красный/Зеленый/Желтый/Оранжевый/Фиолетовый) [" + b.getBuyerPreferredColor() + "]: ", carshowroom.model.enums.CarColor.class, false));
            b.setBuyerPreferredCarReleaseDate(inputReader.readNonRequiredDate("  Новый год выпуска [" + b.getBuyerPreferredCarReleaseDate() + "]: ", b.getBuyerPreferredCarReleaseDate()));
            b.setBuyerPreferredCarMaxMileage(inputReader.readOptionalPositiveInt("  Новый пробег в км [" + b.getBuyerPreferredCarMaxMileage() + "]: ", b.getBuyerPreferredCarMaxMileage()));
            b.setBuyerPreferredCarMaxPrice(inputReader.readBigDecimal("  Новая цена в рублях [" + b.getBuyerPreferredCarMaxPrice() + "]: ", b.getBuyerPreferredCarMaxPrice()));
            buyerService.updateBuyer(b);

            System.out.println("  Покупатель обновлён.");
        }, () -> System.out.println("  Покупатель с ID " + id + " не найден."));
    }

    private void deleteBuyer() {
        int id = inputReader.readPositiveInt("  Введите ID покупателя для удаления: ");

        buyerService.deleteBuyer(id);

        System.out.println("  Покупатель удалён.");
    }

    // ============================================= АВТОМОБИЛИ ==================================================
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

            int input = inputReader.readPositiveInt("Выберите действие: ");

            try {
                switch (input) {
                    case 1 -> printAllCars();
                    case 2 -> findCarById();
                    case 3 -> searchCarsByBrand();
                    case 4 -> searchCarsByCondition();
                    case 5 -> searchCarsByBodyType();
                    case 6 -> createCar();
                    case 7 -> updateCar();
                    case 8 -> deleteCar();
                    case 0 -> back = true;
                    default -> System.out.println("   Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printAllCars() {
        System.out.println("--- Список автомобилей ---");

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
        int id = inputReader.readPositiveInt("  Введите ID автомобиля: ");

        carService.getCarById(id).ifPresentOrElse(
                c -> System.out.println("  Найден: " + c),
                () -> System.out.println("  Автомобиль с ID " + id + " не найден."));
    }

    private void searchCarsByBrand() {
        String brand = inputReader.readRequiredString("  Введите бренд: ");
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
        CarCondition condition = inputReader.readEnum("  Введите состояние (Новый/Б/У): ", CarCondition.class, true);
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
        CarBodyType bodyType = inputReader.readEnum("  Введите тип кузова: ", CarBodyType.class, true);
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
        System.out.println("--- Добавление автомобиля ---");

        Car c = new Car();
        c.setCarBrand(inputReader.readRequiredString("  Марка: "));
        c.setCarModel(inputReader.readRequiredString("  Модель: "));
        c.setCarReleaseDate(inputReader.readDate());
        c.setCarCondition(inputReader.readEnum("  Состояние (Новый/Б/У): ", carshowroom.model.enums.CarCondition.class, true));
        c.setCarMileage(inputReader.readPositiveInt("  Пробег в км: "));
        c.setCarBodyType(inputReader.readEnum("  Тип кузова (Хэтчбек/Седан/Купе/Кабриолет/Универсал/Внедорожник/Кроссовер/Вэн): ",
                carshowroom.model.enums.CarBodyType.class, true));
        c.setCarWheelDrive(inputReader.readEnum("  Привод (Передний/Задний/Полный): ", carshowroom.model.enums.CarWheelDrive.class, true));
        c.setCarGearbox(inputReader.readEnum("  КПП (АКПП/МКПП): ", carshowroom.model.enums.CarGearbox.class, true));
        c.setCarCompletion(inputReader.readEnum("  Комплектация (Базовая/Средняя/Максимальная): ", carshowroom.model.enums.CarCompletion.class, true));
        c.setCarColor(inputReader.readEnum("  Цвет (Белый/Серый/Черный/Синий/Красный/Зеленый/Желтый/Оранжевый/Фиолетовый): ",
                carshowroom.model.enums.CarColor.class, true));
        c.setCarEngineCapacity(inputReader.readBigDecimal("  Объём двигателя: "));
        c.setCarHorsePower(inputReader.readPositiveInt("  Мощность: "));
        c.setCarPrice(inputReader.readBigDecimal("  Цена в рублях: "));
        carService.createCar(c);

        System.out.println("  Автомобиль добавлен. ID: " + c.getCarId());
    }

    private void updateCar() {
        int id = inputReader.readPositiveInt("  Введите ID автомобиля для изменения: ");

        carService.getCarById(id).ifPresentOrElse(c -> {
            System.out.println("  Текущие данные: " + c);

            c.setCarBrand(inputReader.readNonRequiredString("  Новая марка [" + c.getCarBrand() + "]: ", c.getCarBrand()));
            c.setCarModel(inputReader.readNonRequiredString("  Новая модель [" + c.getCarModel() + "]: ", c.getCarModel()));
            c.setCarReleaseDate(inputReader.readDate());
            c.setCarCondition(inputReader.readEnum("  Состояние: ", carshowroom.model.enums.CarCondition.class, true));
            c.setCarMileage(inputReader.readPositiveInt("  Пробег: "));
            c.setCarBodyType(inputReader.readEnum("  Тип кузова: ", carshowroom.model.enums.CarBodyType.class, true));
            c.setCarWheelDrive(inputReader.readEnum("  Привод: ", carshowroom.model.enums.CarWheelDrive.class, true));
            c.setCarGearbox(inputReader.readEnum("  КПП: ", carshowroom.model.enums.CarGearbox.class, true));
            c.setCarCompletion(inputReader.readEnum("  Комплектация: ", carshowroom.model.enums.CarCompletion.class, true));
            c.setCarColor(inputReader.readEnum("  Цвет: ", carshowroom.model.enums.CarColor.class, true));
            c.setCarEngineCapacity(inputReader.readBigDecimal("  Объём двигателя: "));
            c.setCarHorsePower(inputReader.readPositiveInt("  Мощность: "));
            c.setCarPrice(inputReader.readBigDecimal("  Цена: "));
            carService.updateCar(c);

            System.out.println("  Автомобиль обновлён.");
        }, () -> System.out.println("  Автомобиль с ID " + id + " не найден."));
    }

    private void deleteCar() {
        int id = inputReader.readPositiveInt("  Введите ID автомобиля для удаления: ");

        carService.deleteCar(id);

        System.out.println("  Автомобиль удалён.");
    }

    // ================================================ ЗАКАЗЫ ====================================================
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

            int input = inputReader.readPositiveInt("Выберите действие: ");

            try {
                switch (input) {
                    case 1 -> printAllOrders();
                    case 2 -> findOrderById();
                    case 3 -> createOrder();
                    case 4 -> updateOrder();
                    case 5 -> deleteOrder();
                    case 0 -> back = true;
                    default -> System.out.println("   Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printAllOrders() {
        System.out.println("--- Список заказов ---");

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
        int id = inputReader.readPositiveInt("  Введите ID заказа: ");

        orderService.getOrderById(id).ifPresentOrElse(
                o -> System.out.println("  Найден заказ ID=" + o.getOrderId() +
                        ", покупатель ID=" + o.getBuyerId() +
                        ", автомобиль ID=" + o.getCarId() +
                        ", статус=" + o.getOrderStatus().getValue()),
                () -> System.out.println("  Заказ с ID " + id + " не найден."));
    }

    private void createOrder() {
        System.out.println("--- Добавление заказа ---");

        Order o = new Order();
        o.setBuyerId(inputReader.readPositiveInt("  ID покупателя: "));
        o.setCarId(inputReader.readPositiveInt("  ID автомобиля: "));
        o.setOrderStatus(inputReader.readEnum("  Статус (Ожидает/Подтверждён/Завершён/Отменён): ",
                carshowroom.model.enums.OrderStatus.class, true));
        o.setOrderDate(inputReader.readDate());
        o.setOrderSum(inputReader.readBigDecimal("  Сумма заказа (руб.): "));
        orderService.createOrder(o);

        System.out.println("  Заказ добавлен. ID: " + o.getOrderId());
    }

    private void updateOrder() {
        int id = inputReader.readPositiveInt("  Введите ID заказа для изменения: ");

        orderService.getOrderById(id).ifPresentOrElse(o -> {
            System.out.println("  Текущий заказ: ID=" + o.getOrderId() + ", статус=" + o.getOrderStatus().getValue());
            o.setBuyerId(inputReader.readPositiveInt("  ID покупателя: "));
            o.setCarId(inputReader.readPositiveInt("  ID автомобиля: "));
            o.setOrderStatus(inputReader.readEnum("  Статус: ", carshowroom.model.enums.OrderStatus.class, true));
            o.setOrderDate(inputReader.readDate());
            o.setOrderSum(inputReader.readBigDecimal("  Сумма: "));
            orderService.updateOrder(o);
            System.out.println("  Заказ обновлён.");
        }, () -> System.out.println("  Заказ с ID " + id + " не найден."));
    }

    private void deleteOrder() {
        int id = inputReader.readPositiveInt("  Введите ID заказа для изменения: ");

        orderService.deleteOrder(id);

        System.out.println("  Заказ удалён.");
    }

    // ================================================ ФИЛЬТРАЦИЯ ================================================
    private void showFilterMenu() {
        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("--- Фильтрация ---");
            System.out.println("  1. Фильтрация автомобилей");
            System.out.println("  2. Фильтрация заказов");
            System.out.println("  0. Назад в главное меню");

            int input = inputReader.readPositiveInt("Выберите действие: ");

            try {
                switch (input) {
                    case 1 -> showCarFilterMenu();
                    case 2 -> showOrderFilterMenu();
                    case 0 -> back = true;
                    default -> System.out.println("  Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
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

            int input = inputReader.readPositiveInt("Выберите действие: ");

            try {
                switch (input) {
                    case 1 -> filterCarsByReleaseDate();
                    case 2 -> filterCarsByMileage();
                    case 0 -> back = true;
                    default -> System.out.println("   Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void filterCarsByReleaseDate() {
        System.out.println("--- Автомобили по году выпуска ---");

        List<Car> cars = carService.getCarsSortedByReleaseDateDesc();

        if (cars.isEmpty()) {
            System.out.println("  Список пуст.");
            return;
        }

        System.out.printf("  %-5s %-20s %-20s %-15s %-10s %-12s%n",
                "ID", "Бренд", "Модель", "Год", "Пробег", "Цена");
        System.out.println("  ------------------------------------------------------------------------------------------");

        for (Car c : cars) {
            System.out.printf("  %-5s %-20s %-20s %-15s %-10s %-12s%n",
                    c.getCarId(),
                    c.getCarBrand(),
                    c.getCarModel(),
                    c.getCarReleaseDate() != null ? c.getCarReleaseDate().toString() : "-",
                    c.getCarMileage(),
                    c.getCarPrice() + " руб.");
        }
    }

    private void filterCarsByMileage() {
        System.out.println("--- Автомобили по пробегу ---");

        List<Car> cars = carService.getCarsSortedByMileageDesc();

        if (cars.isEmpty()) {
            System.out.println("  Список пуст.");
            return;
        }

        System.out.printf("  %-5s %-20s %-20s %-15s %-10s %-12s%n",
                "ID", "Бренд", "Модель", "Год", "Пробег", "Цена");
        System.out.println("  ------------------------------------------------------------------------------------------");

        for (Car c : cars) {
            System.out.printf("  %-5d %-20s %-20s %-15s %-10d %-12s%n",
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

            int input = inputReader.readPositiveInt("Выберите действие: ");

            try {
                switch (input) {
                    case 1 -> filterOrdersByDate();
                    case 2 -> filterOrdersBySum();
                    case 0 -> back = true;
                    default -> System.out.println("  Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void filterOrdersByDate() {
        System.out.println("--- Заказы по дате ---");

        List<OrderView> orders = orderService.getOrdersSortedByDateDesc();

        if (orders.isEmpty()) {
            System.out.println("  Список пуст.");
            return;
        }

        System.out.printf("  %-5s %-15s %-35s %-15s %-10s %-25s %-15s %-20s%n",
                "ID", "Статус", "Покупатель", "Телефон", "ID авто", "Автомобиль", "Дата", "Сумма");
        System.out.println("  -----------------------------------------------------------------------------------------------------------------------------------------");

        for (OrderView o : orders) {
            System.out.printf("  %-5s %-15s %-35s %-15s %-10s %-25s %-15s %-20s%n",
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
        System.out.println("--- Заказы по сумме ---");

        List<OrderView> orders = orderService.getOrdersSortedBySumDesc();

        if (orders.isEmpty()) {
            System.out.println("  Список пуст.");
            return;
        }

        System.out.printf("  %-5s %-15s %-35s %-15s %-10s %-25s %-15s %-20s%n",
                "ID", "Статус", "Покупатель", "Телефон", "ID авто", "Автомобиль", "Дата", "Сумма");
        System.out.println("  -----------------------------------------------------------------------------------------------------------------------------------------");

        for (OrderView o : orders) {
            System.out.printf("  %-5s %-15s %-35s %-15s %-10s %-25s %-15s %-20s%n",
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

    // =============================================== СТАТИСТИКА =================================================
    private void showStatistics() {
        System.out.println("--- Статистика ---");
        System.out.println("  1. Количество записей в таблице покупатели: " + buyerService.getBuyersCount());
        System.out.println("  2. Количество записей в таблице автомобили: " + carService.getCarsCount());
        System.out.println("  3. Количество записей в таблице заказы: " + orderService.getOrdersCount());
        System.out.println("  4. Количество ожидаемых заказов: " + orderService.getOrdersCountByStatus(carshowroom.model.enums.OrderStatus.PENDING));
        System.out.println("  5. Количество подтверждённых заказов: " + orderService.getOrdersCountByStatus(carshowroom.model.enums.OrderStatus.CONFIRMED));
        System.out.println("  6. Количество завершённых заказов: " + orderService.getOrdersCountByStatus(carshowroom.model.enums.OrderStatus.COMPLETED));
        System.out.println("  7. Количество отменённых заказов: " + orderService.getOrdersCountByStatus(carshowroom.model.enums.OrderStatus.CANCELLED));
    }
}