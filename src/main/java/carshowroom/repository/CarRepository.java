package carshowroom.repository;

import carshowroom.exception.DatabaseException;
import carshowroom.exception.EntityNotFoundException;
import carshowroom.model.Car;
import carshowroom.model.enums.*;
import carshowroom.util.DatabaseManager;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarRepository implements Repository<Car> {
    private final DatabaseManager dbManager;

    public CarRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars ORDER BY car_id";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) cars.add(mapRow(rs));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка получения списка автомобилей: " + e.getMessage(), e);
        }
        return cars;
    }

    @Override
    public Optional<Car> findById(int id) {
        String sql = "SELECT * FROM cars WHERE car_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка поиска автомобиля по ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Car> findByBrand(String brand) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE car_brand = ? ORDER BY car_id";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, brand);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) cars.add(mapRow(rs));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка поиска автомобилей по бренду: " + e.getMessage(), e);
        }
        return cars;
    }

    public List<Car> findByCondition(CarCondition condition) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE car_condition = ?::car_condition ORDER BY car_id";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, condition.getValue());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) cars.add(mapRow(rs));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка поиска автомобилей по состоянию: " + e.getMessage(), e);
        }
        return cars;
    }

    public List<Car> findByBodyType(CarBodyType bodyType) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE car_body_type = ?::car_body_type ORDER BY car_id";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bodyType.getValue());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) cars.add(mapRow(rs));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка поиска автомобилей по типу кузова: " + e.getMessage(), e);
        }
        return cars;
    }

    public List<Car> findAllOrderByReleaseDateDesc() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars ORDER BY car_release_date DESC";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) cars.add(mapRow(rs));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка фильтрации автомобилей по году: " + e.getMessage(), e);
        }
        return cars;
    }

    public List<Car> findAllOrderByMileageDesc() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars ORDER BY car_mileage DESC";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) cars.add(mapRow(rs));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка фильтрации автомобилей по пробегу: " + e.getMessage(), e);
        }
        return cars;
    }

    public long countAll() {
        String sql = "SELECT COUNT(*) FROM cars";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подсчёта автомобилей: " + e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public Car save(Car car) {
        String sql = "INSERT INTO cars (car_brand, car_model, car_release_date, car_condition, " +
                "car_mileage, car_body_type, car_wheel_drive, car_gearbox, car_completion, car_color, " +
                "car_engine_capacity, car_horse_power, car_price) " +
                "VALUES (?, ?, ?, ?::car_condition, ?, ?::car_body_type, ?::car_wheel_drive, " +
                "?::car_gearbox, ?::car_completion, ?::car_color, ?, ?, ?) RETURNING car_id";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParams(stmt, car);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) car.setCarId(rs.getInt("car_id"));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка сохранения автомобиля: " + e.getMessage(), e);
        }
        return car;
    }

    @Override
    public Car update(Car car) {
        String sql = "UPDATE cars SET car_brand=?, car_model=?, car_release_date=?, " +
                "car_condition=?::car_condition, car_mileage=?, car_body_type=?::car_body_type, " +
                "car_wheel_drive=?::car_wheel_drive, car_gearbox=?::car_gearbox, " +
                "car_completion=?::car_completion, car_color=?::car_color, " +
                "car_engine_capacity=?, car_horse_power=?, car_price=?, updated_at=CURRENT_TIMESTAMP " +
                "WHERE car_id=?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParams(stmt, car);
            stmt.setInt(14, car.getCarId());
            if (stmt.executeUpdate() == 0)
                throw new EntityNotFoundException("Автомобиль с ID " + car.getCarId() + " не найден");
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка обновления автомобиля: " + e.getMessage(), e);
        }
        return car;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM cars WHERE car_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            if (stmt.executeUpdate() == 0)
                throw new EntityNotFoundException("Автомобиль с ID " + id + " не найден");
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка удаления автомобиля: " + e.getMessage(), e);
        }
    }

    private Car mapRow(ResultSet rs) throws SQLException {
        Car c = new Car();
        c.setCarId(rs.getInt("car_id"));
        c.setCarBrand(rs.getString("car_brand"));
        c.setCarModel(rs.getString("car_model"));

        Date d = rs.getDate("car_release_date");
        if (d != null) c.setCarReleaseDate(d.toLocalDate());

        c.setCarCondition(CarCondition.fromValue(rs.getString("car_condition")));
        c.setCarMileage(rs.getInt("car_mileage"));
        c.setCarBodyType(CarBodyType.fromValue(rs.getString("car_body_type")));
        c.setCarWheelDrive(CarWheelDrive.fromValue(rs.getString("car_wheel_drive")));
        c.setCarGearbox(CarGearbox.fromValue(rs.getString("car_gearbox")));
        c.setCarCompletion(CarCompletion.fromValue(rs.getString("car_completion")));
        c.setCarColor(CarColor.fromValue(rs.getString("car_color")));
        c.setCarEngineCapacity(rs.getBigDecimal("car_engine_capacity"));
        c.setCarHorsePower(rs.getInt("car_horse_power"));
        c.setCarPrice(rs.getBigDecimal("car_price"));
        return c;
    }

    private void setParams(PreparedStatement stmt, Car c) throws SQLException {
        stmt.setString(1, c.getCarBrand());
        stmt.setString(2, c.getCarModel());
        stmt.setDate(3, Date.valueOf(c.getCarReleaseDate()));
        stmt.setString(4, c.getCarCondition().getValue());
        stmt.setInt(5, c.getCarMileage());
        stmt.setString(6, c.getCarBodyType().getValue());
        stmt.setString(7, c.getCarWheelDrive().getValue());
        stmt.setString(8, c.getCarGearbox().getValue());
        stmt.setString(9, c.getCarCompletion().getValue());
        stmt.setString(10, c.getCarColor().getValue());
        stmt.setBigDecimal(11, c.getCarEngineCapacity());
        stmt.setInt(12, c.getCarHorsePower());
        stmt.setBigDecimal(13, c.getCarPrice());
    }
}