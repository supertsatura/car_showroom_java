package carshowroom.service;

import carshowroom.exception.BusinessException;
import carshowroom.model.Order;
import carshowroom.model.OrderView;
import carshowroom.model.enums.OrderStatus;
import carshowroom.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    //public List<Order> getAllOrders() {
    //    return repository.findAll();
    //}

    public List<OrderView> getAllOrdersWithDetails() {
        return repository.findAllWithDetails();
    }

    public Optional<Order> getOrderById(int id) {
        return repository.findById(id);
    }

    public List<OrderView> getOrdersSortedByDateDesc() {
        return repository.findAllOrderByDateDesc();
    }

    public List<OrderView> getOrdersSortedBySumDesc() {
        return repository.findAllOrderBySumDesc();
    }

    public long getOrdersCount() {
        return repository.countAll();
    }

    public long getOrdersCountByStatus(OrderStatus status) {
        return repository.countByStatus(status);
    }

    public Order createOrder(Order order) {
        validateOrder(order);
        return repository.save(order);
    }

    public Order updateOrder(Order order) {
        validateOrder(order);
        return repository.update(order);
    }

    public void deleteOrder(int id) {
        repository.deleteById(id);
    }

    private void validateOrder(Order order) {
        if (order.getOrderSum() == null || order.getOrderSum().compareTo(BigDecimal.ZERO) < 0)
            throw new BusinessException("Сумма заказа не может быть отрицательной");
        if (order.getOrderDate() == null)
            throw new BusinessException("Дата заказа обязательна");
    }
}