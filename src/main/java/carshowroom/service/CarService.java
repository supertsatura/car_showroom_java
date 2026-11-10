package carshowroom.service;

import carshowroom.exception.BusinessException;
import carshowroom.model.Car;
import carshowroom.model.enums.CarBodyType;
import carshowroom.model.enums.CarCondition;
import carshowroom.repository.CarRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        if (car.getCarReleaseDate().isAfter(LocalDate.now())) {
            throw new BusinessException("Дата выпуска автомобиля не может быть позже текущей даты");
        }

        return repository.save(car);
    }

    public Car updateCar(Car car) {
        if (car.getCarReleaseDate().isAfter(LocalDate.now())) {
            throw new BusinessException("Дата выпуска автомобиля не может быть позже текущей даты");
        }

        return repository.update(car);
    }

    public void deleteCar(int id) {
        repository.deleteById(id);
    }
}