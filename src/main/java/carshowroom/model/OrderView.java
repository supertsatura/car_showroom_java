package carshowroom.model;

import carshowroom.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderView {
    private int orderId;
    private int buyerId;
    private String buyerSurname;
    private String buyerName;
    private String buyerPatronymic;
    private String buyerTel;
    private int carId;
    private String carBrand;
    private String carModel;
    private OrderStatus orderStatus;
    private LocalDate orderDate;
    private BigDecimal orderSum;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerSurname() {
        return buyerSurname;
    }

    public void setBuyerSurname(String buyerSurname) {
        this.buyerSurname = buyerSurname;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPatronymic() {
        return buyerPatronymic;
    }

    public void setBuyerPatronymic(String buyerPatronymic) {
        this.buyerPatronymic = buyerPatronymic;
    }

    public String getBuyerTel() {
        return buyerTel;
    }

    public void setBuyerTel(String buyerTel) {
        this.buyerTel = buyerTel;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(BigDecimal orderSum) {
        this.orderSum = orderSum;
    }

    public String getBuyerFullName() {
        return buyerSurname + " " + buyerName + (buyerPatronymic != null ? " " + buyerPatronymic : "");
    }

    public String getCarFullName() {
        return carBrand + " " + carModel;
    }
}