package carshowroom.service;

import carshowroom.exception.BusinessException;
import carshowroom.exception.EntityNotFoundException;
import carshowroom.model.Order;
import carshowroom.model.OrderView;
import carshowroom.model.enums.OrderStatus;
import carshowroom.repository.BuyerRepository;
import carshowroom.repository.CarRepository;
import carshowroom.repository.OrderRepository;

import java.util.List;
import java.util.Optional;


public class OrderService {
    private final OrderRepository orderRepository;
    private final BuyerRepository buyerRepository;
    private final CarRepository carRepository;

    public OrderService(OrderRepository orderRepository, BuyerRepository buyerRepository, CarRepository carRepository) {
        this.orderRepository = orderRepository;
        this.buyerRepository = buyerRepository;
        this.carRepository = carRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderView> getAllOrdersWithDetails() {
        return orderRepository.findAllWithDetails();
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    public List<OrderView> getOrdersSortedByDateDesc() {
        return orderRepository.findAllOrderByDateDesc();
    }

    public List<OrderView> getOrdersSortedBySumDesc() {
        return orderRepository.findAllOrderBySumDesc();
    }

    public long getOrdersCount() {
        return orderRepository.countAll();
    }

    public long getOrdersCountByStatus(OrderStatus status) {
        return orderRepository.countByStatus(status);
    }

    public Order createOrder(Order order) {

        if (buyerRepository.findById(order.getBuyerId()).isEmpty()) {
            throw new BusinessException("Покупатель с ID " + order.getBuyerId() + " не найден");
        }

        if (carRepository.findById(order.getCarId()).isEmpty()) {
            throw new BusinessException("Автомобиль с ID " + order.getCarId() + " не найден");
        }

        return orderRepository.save(order);
    }

    public Order updateOrder(Order order) {

        Order existingOrder = orderRepository.findById(order.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Заказ с ID " + order.getOrderId() + " не найден"));

        if (existingOrder.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new BusinessException("Невозможно изменить завершенный заказ");
        }

        if (existingOrder.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new BusinessException("Невозможно изменить отмененный заказ");
        }

        if (buyerRepository.findById(order.getBuyerId()).isEmpty()) {
            throw new BusinessException("Покупатель с ID " + order.getBuyerId() + " не найден");
        }

        if (carRepository.findById(order.getCarId()).isEmpty()) {
            throw new BusinessException("Автомобиль с ID " + order.getCarId() + " не найден");
        }

        return orderRepository.update(order);
    }

    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
}