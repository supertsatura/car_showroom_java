package carshowroom.repository;

import carshowroom.exception.DatabaseException;
import carshowroom.exception.EntityNotFoundException;
import carshowroom.model.Order;
import carshowroom.model.OrderView;
import carshowroom.model.enums.OrderStatus;
import carshowroom.util.DatabaseManager;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository implements Repository<Order> {
    private final DatabaseManager dbManager;

    public OrderRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_id";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) orders.add(mapRow(rs));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка получения списка заказов: " + e.getMessage(), e);
        }
        return orders;
    }

    public List<OrderView> findAllWithDetails() {
        List<OrderView> list = new ArrayList<>();
        String sql = "SELECT o.order_id, o.order_status, o.order_date, o.order_sum, " +
                "b.buyer_id, b.buyer_surname, b.buyer_name, b.buyer_patronymic, b.buyer_tel, " +
                "c.car_id, c.car_brand, c.car_model " +
                "FROM orders o " +
                "JOIN buyers b ON o.buyer_id = b.buyer_id " +
                "JOIN cars c ON o.car_id = c.car_id " +
                "ORDER BY o.order_id";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OrderView v = new OrderView();
                v.setOrderId(rs.getInt("order_id"));
                v.setOrderStatus(OrderStatus.fromValue(rs.getString("order_status")));
                Date od = rs.getDate("order_date");
                if (od != null) v.setOrderDate(od.toLocalDate());
                v.setOrderSum(rs.getBigDecimal("order_sum"));
                v.setBuyerId(rs.getInt("buyer_id"));
                v.setBuyerSurname(rs.getString("buyer_surname"));
                v.setBuyerName(rs.getString("buyer_name"));
                v.setBuyerPatronymic(rs.getString("buyer_patronymic"));
                v.setBuyerTel(rs.getString("buyer_tel"));
                v.setCarId(rs.getInt("car_id"));
                v.setCarBrand(rs.getString("car_brand"));
                v.setCarModel(rs.getString("car_model"));
                list.add(v);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка получения заказов с деталями: " + e.getMessage(), e);
        }
        return list;
    }

    public List<OrderView> findAllOrderByDateDesc() {
        List<OrderView> list = new ArrayList<>();
        String sql = "SELECT o.order_id, o.order_status, o.order_date, o.order_sum, " +
                "b.buyer_id, b.buyer_surname, b.buyer_name, b.buyer_patronymic, b.buyer_tel, " +
                "c.car_id, c.car_brand, c.car_model " +
                "FROM orders o " +
                "JOIN buyers b ON o.buyer_id = b.buyer_id " +
                "JOIN cars c ON o.car_id = c.car_id " +
                "ORDER BY o.order_date DESC";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OrderView v = new OrderView();
                v.setOrderId(rs.getInt("order_id"));
                v.setOrderStatus(OrderStatus.fromValue(rs.getString("order_status")));
                Date od = rs.getDate("order_date");
                if (od != null) v.setOrderDate(od.toLocalDate());
                v.setOrderSum(rs.getBigDecimal("order_sum"));
                v.setBuyerId(rs.getInt("buyer_id"));
                v.setBuyerSurname(rs.getString("buyer_surname"));
                v.setBuyerName(rs.getString("buyer_name"));
                v.setBuyerPatronymic(rs.getString("buyer_patronymic"));
                v.setBuyerTel(rs.getString("buyer_tel"));
                v.setCarId(rs.getInt("car_id"));
                v.setCarBrand(rs.getString("car_brand"));
                v.setCarModel(rs.getString("car_model"));
                list.add(v);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка фильтрации заказов по дате: " + e.getMessage(), e);
        }
        return list;
    }

    public List<OrderView> findAllOrderBySumDesc() {
        List<OrderView> list = new ArrayList<>();
        String sql = "SELECT o.order_id, o.order_status, o.order_date, o.order_sum, " +
                "b.buyer_id, b.buyer_surname, b.buyer_name, b.buyer_patronymic, b.buyer_tel, " +
                "c.car_id, c.car_brand, c.car_model " +
                "FROM orders o " +
                "JOIN buyers b ON o.buyer_id = b.buyer_id " +
                "JOIN cars c ON o.car_id = c.car_id " +
                "ORDER BY o.order_sum DESC";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OrderView v = new OrderView();
                v.setOrderId(rs.getInt("order_id"));
                v.setOrderStatus(OrderStatus.fromValue(rs.getString("order_status")));
                Date od = rs.getDate("order_date");
                if (od != null) v.setOrderDate(od.toLocalDate());
                v.setOrderSum(rs.getBigDecimal("order_sum"));
                v.setBuyerId(rs.getInt("buyer_id"));
                v.setBuyerSurname(rs.getString("buyer_surname"));
                v.setBuyerName(rs.getString("buyer_name"));
                v.setBuyerPatronymic(rs.getString("buyer_patronymic"));
                v.setBuyerTel(rs.getString("buyer_tel"));
                v.setCarId(rs.getInt("car_id"));
                v.setCarBrand(rs.getString("car_brand"));
                v.setCarModel(rs.getString("car_model"));
                list.add(v);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка фильтрации заказов по сумме: " + e.getMessage(), e);
        }
        return list;
    }

    public long countAll() {
        String sql = "SELECT COUNT(*) FROM orders";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подсчёта заказов: " + e.getMessage(), e);
        }
        return 0;
    }

    public long countByStatus(OrderStatus status) {
        String sql = "SELECT COUNT(*) FROM orders WHERE order_status = ?::order_status";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.getValue());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подсчёта заказов по статусу: " + e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public Optional<Order> findById(int id) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка поиска заказа по ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Order save(Order order) {
        String sql = "INSERT INTO orders (buyer_id, car_id, order_status, order_date, order_sum) " +
                "VALUES (?,?,?::order_status,?,?) RETURNING order_id";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getBuyerId());
            stmt.setInt(2, order.getCarId());
            stmt.setString(3, order.getOrderStatus().getValue());
            stmt.setDate(4, Date.valueOf(order.getOrderDate()));
            stmt.setBigDecimal(5, order.getOrderSum());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) order.setOrderId(rs.getInt("order_id"));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка сохранения заказа: " + e.getMessage(), e);
        }
        return order;
    }

    @Override
    public Order update(Order order) {
        String sql = "UPDATE orders SET buyer_id=?, car_id=?, order_status=?::order_status, order_date=?, " +
                "order_sum=?, updated_at=CURRENT_TIMESTAMP WHERE order_id=?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getBuyerId());
            stmt.setInt(2, order.getCarId());
            stmt.setString(3, order.getOrderStatus().getValue());
            stmt.setDate(4, Date.valueOf(order.getOrderDate()));
            stmt.setBigDecimal(5, order.getOrderSum());
            stmt.setInt(6, order.getOrderId());
            if (stmt.executeUpdate() == 0)
                throw new EntityNotFoundException("Заказ с ID " + order.getOrderId() + " не найден");
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка обновления заказа: " + e.getMessage(), e);
        }
        return order;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            if (stmt.executeUpdate() == 0)
                throw new EntityNotFoundException("Заказ с ID " + id + " не найден");
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка удаления заказа: " + e.getMessage(), e);
        }
    }

    private Order mapRow(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setOrderId(rs.getInt("order_id"));
        o.setBuyerId(rs.getInt("buyer_id"));
        o.setCarId(rs.getInt("car_id"));
        o.setOrderStatus(OrderStatus.fromValue(rs.getString("order_status")));
        Date d = rs.getDate("order_date");
        if (d != null) o.setOrderDate(d.toLocalDate());
        o.setOrderSum(rs.getBigDecimal("order_sum"));
        return o;
    }
}