package carshowroom.model;

import carshowroom.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {
    private int orderId;
    private int buyerId;
    private int carId;
    private OrderStatus orderStatus;
    private LocalDate orderDate;
    private BigDecimal orderSum;

    public Order() {
    }

    public Order(int orderId, int buyerId, int carId, OrderStatus orderStatus, LocalDate orderDate, BigDecimal orderSum) {
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.carId = carId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderSum = orderSum;
    }

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

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
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
}