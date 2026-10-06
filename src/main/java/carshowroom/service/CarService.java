package carshowroom.service;

import carshowroom.exception.BusinessException;
import carshowroom.model.Car;
import carshowroom.model.enums.CarBodyType;
import carshowroom.model.enums.CarCondition;
import carshowroom.repository.CarRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CarService {
    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public List<Car> getAllCars() {
        return repository.findAll();
    }

    public Optional<Car> getCarById(int id) {
        return repository.findById(id);
    }

    public List<Car> getCarsByBrand(String brand) {
        return repository.findByBrand(brand);
    }

    public List<Car> getCarsByCondition(CarCondition condition) {
        return repository.findByCondition(condition);
    }

    public List<Car> getCarsByBodyType(CarBodyType bodyType) {
        return repository.findByBodyType(bodyType);
    }

    public List<Car> getCarsSortedByReleaseDateDesc() {
        return repository.findAllOrderByReleaseDateDesc();
    }

    public List<Car> getCarsSortedByMileageDesc() {
        return repository.findAllOrderByMileageDesc();
    }

    public long getCarsCount() {
        return repository.countAll();
    }

    public Car createCar(Car car) {
        validateCar(car);
        return repository.save(car);
    }

    public Car updateCar(Car car) {
        validateCar(car);
        return repository.update(car);
    }

    public void deleteCar(int id) {
        repository.deleteById(id);
    }

    private void validateCar(Car car) {
        if (car.getCarBrand() == null || car.getCarBrand().isBlank())
            throw new BusinessException("Марка автомобиля не может быть пустой");
        if (car.getCarModel() == null || car.getCarModel().isBlank())
            throw new BusinessException("Модель автомобиля не может быть пустой");
        if (car.getCarPrice() == null || car.getCarPrice().compareTo(BigDecimal.ZERO) < 0)
            throw new BusinessException("Цена автомобиля не может быть отрицательной");
        if (car.getCarMileage() < 0)
            throw new BusinessException("Пробег автомобиля не может быть отрицательным");
        if (car.getCarEngineCapacity().compareTo(new BigDecimal("0.1")) < 0 ||
                car.getCarEngineCapacity().compareTo(new BigDecimal("9.9")) > 0)
            throw new BusinessException("Объём двигателя должен быть от 0.1 до 9.9 л");
        if (car.getCarHorsePower() < 1 || car.getCarHorsePower() > 2000)
            throw new BusinessException("Мощность двигателя должна быть от 1 до 2000 л.с.");
    }
}