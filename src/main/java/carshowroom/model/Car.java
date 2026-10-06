package carshowroom.model;

import carshowroom.model.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Car {
    private int carId;
    private String carBrand;
    private String carModel;
    private LocalDate carReleaseDate;
    private CarCondition carCondition;
    private int carMileage;
    private CarBodyType carBodyType;
    private CarWheelDrive carWheelDrive;
    private CarGearbox carGearbox;
    private CarCompletion carCompletion;
    private CarColor carColor;
    private BigDecimal carEngineCapacity;
    private int carHorsePower;
    private BigDecimal carPrice;

    public Car() {
    }

    public Car(int carId, String carBrand, String carModel, LocalDate carReleaseDate, CarCondition carCondition, int carMileage, CarBodyType carBodyType, CarWheelDrive carWheelDrive, CarGearbox carGearbox, CarCompletion carCompletion, CarColor carColor, BigDecimal carEngineCapacity, int carHorsePower, BigDecimal carPrice) {
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carReleaseDate = carReleaseDate;
        this.carCondition = carCondition;
        this.carMileage = carMileage;
        this.carBodyType = carBodyType;
        this.carWheelDrive = carWheelDrive;
        this.carGearbox = carGearbox;
        this.carCompletion = carCompletion;
        this.carColor = carColor;
        this.carEngineCapacity = carEngineCapacity;
        this.carHorsePower = carHorsePower;
        this.carPrice = carPrice;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public LocalDate getCarReleaseDate() {
        return carReleaseDate;
    }

    public void setCarReleaseDate(LocalDate carReleaseDate) {
        this.carReleaseDate = carReleaseDate;
    }

    public CarCondition getCarCondition() {
        return carCondition;
    }

    public void setCarCondition(CarCondition carCondition) {
        this.carCondition = carCondition;
    }

    public int getCarMileage() {
        return carMileage;
    }

    public void setCarMileage(int carMileage) {
        this.carMileage = carMileage;
    }

    public CarBodyType getCarBodyType() {
        return carBodyType;
    }

    public void setCarBodyType(CarBodyType carBodyType) {
        this.carBodyType = carBodyType;
    }

    public CarWheelDrive getCarWheelDrive() {
        return carWheelDrive;
    }

    public void setCarWheelDrive(CarWheelDrive carWheelDrive) {
        this.carWheelDrive = carWheelDrive;
    }

    public CarGearbox getCarGearbox() {
        return carGearbox;
    }

    public void setCarGearbox(CarGearbox carGearbox) {
        this.carGearbox = carGearbox;
    }

    public CarCompletion getCarCompletion() {
        return carCompletion;
    }

    public void setCarCompletion(CarCompletion carCompletion) {
        this.carCompletion = carCompletion;
    }

    public CarColor getCarColor() {
        return carColor;
    }

    public void setCarColor(CarColor carColor) {
        this.carColor = carColor;
    }

    public BigDecimal getCarEngineCapacity() {
        return carEngineCapacity;
    }

    public void setCarEngineCapacity(BigDecimal carEngineCapacity) {
        this.carEngineCapacity = carEngineCapacity;
    }

    public int getCarHorsePower() {
        return carHorsePower;
    }

    public void setCarHorsePower(int carHorsePower) {
        this.carHorsePower = carHorsePower;
    }

    public BigDecimal getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(BigDecimal carPrice) {
        this.carPrice = carPrice;
    }

    @Override
    public String toString() {
        return carBrand + " " + carModel +
                " (" + (carReleaseDate != null ? carReleaseDate.getYear() : "-") + " г.) | " +
                "Пробег: " + carMileage + " км | " +
                carPrice + " руб.";
    }
}