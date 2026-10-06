package carshowroom.repository;

import carshowroom.exception.DatabaseException;
import carshowroom.exception.EntityNotFoundException;
import carshowroom.model.Buyer;
import carshowroom.model.enums.*;
import carshowroom.util.DatabaseManager;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuyerRepository implements Repository<Buyer> {
    private final DatabaseManager dbManager;

    public BuyerRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public List<Buyer> findAll() {
        List<Buyer> buyers = new ArrayList<>();
        String sql = "SELECT * FROM buyers ORDER BY buyer_id";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) buyers.add(mapRow(rs));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка получения списка покупателей: " + e.getMessage(), e);
        }
        return buyers;
    }

    @Override
    public Optional<Buyer> findById(int id) {
        String sql = "SELECT * FROM buyers WHERE buyer_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка поиска покупателя по ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Buyer> findByPreferredBrand(String brand) {
        List<Buyer> buyers = new ArrayList<>();
        String sql = "SELECT * FROM buyers WHERE buyer_preferred_brand = ? ORDER BY buyer_id";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, brand);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) buyers.add(mapRow(rs));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка поиска покупателей по бренду: " + e.getMessage(), e);
        }
        return buyers;
    }

    public long countAll() {
        String sql = "SELECT COUNT(*) FROM buyers";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подсчёта покупателей: " + e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public Buyer save(Buyer buyer) {
        String sql = "INSERT INTO buyers (buyer_surname, buyer_name, buyer_patronymic, buyer_tel, " +
                "buyer_preferred_brand, buyer_preferred_model, buyer_preferred_body_type, " +
                "buyer_preferred_wheel_drive, buyer_preferred_gearbox, buyer_preferred_condition, " +
                "buyer_preferred_completion, buyer_preferred_color, buyer_car_release_date, " +
                "buyer_max_mileage, buyer_max_price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?::car_body_type, ?::car_wheel_drive, ?::car_gearbox, " +
                "?::car_condition, ?::car_completion, ?::car_color, ?, ?, ?) RETURNING buyer_id";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParams(stmt, buyer);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) buyer.setBuyerId(rs.getInt("buyer_id"));
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка сохранения покупателя: " + e.getMessage(), e);
        }
        return buyer;
    }

    @Override
    public Buyer update(Buyer buyer) {
        String sql = "UPDATE buyers SET buyer_surname=?, buyer_name=?, buyer_patronymic=?, buyer_tel=?, " +
                "buyer_preferred_brand=?, buyer_preferred_model=?, " +
                "buyer_preferred_body_type=?::car_body_type, " +
                "buyer_preferred_wheel_drive=?::car_wheel_drive, " +
                "buyer_preferred_gearbox=?::car_gearbox, " +
                "buyer_preferred_condition=?::car_condition, " +
                "buyer_preferred_completion=?::car_completion, " +
                "buyer_preferred_color=?::car_color, " +
                "buyer_car_release_date=?, buyer_max_mileage=?, buyer_max_price=?, " +
                "updated_at=CURRENT_TIMESTAMP WHERE buyer_id=?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParams(stmt, buyer);
            stmt.setInt(16, buyer.getBuyerId());
            if (stmt.executeUpdate() == 0)
                throw new EntityNotFoundException("Покупатель с ID " + buyer.getBuyerId() + " не найден");
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка обновления покупателя: " + e.getMessage(), e);
        }
        return buyer;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM buyers WHERE buyer_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            if (stmt.executeUpdate() == 0)
                throw new EntityNotFoundException("Покупатель с ID " + id + " не найден");
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка удаления покупателя: " + e.getMessage(), e);
        }
    }

    private Buyer mapRow(ResultSet rs) throws SQLException {
        Buyer b = new Buyer();
        b.setBuyerId(rs.getInt("buyer_id"));
        b.setBuyerSurname(rs.getString("buyer_surname"));
        b.setBuyerName(rs.getString("buyer_name"));
        b.setBuyerPatronymic(rs.getString("buyer_patronymic"));
        b.setBuyerTel(rs.getString("buyer_tel"));
        b.setBuyerPreferredBrand(rs.getString("buyer_preferred_brand"));
        b.setBuyerPreferredModel(rs.getString("buyer_preferred_model"));

        String v;
        v = rs.getString("buyer_preferred_body_type");
        if (v != null) b.setBuyerPreferredBodyType(CarBodyType.fromValue(v));
        v = rs.getString("buyer_preferred_wheel_drive");
        if (v != null) b.setBuyerPreferredWheelDrive(CarWheelDrive.fromValue(v));
        v = rs.getString("buyer_preferred_gearbox");
        if (v != null) b.setBuyerPreferredGearbox(CarGearbox.fromValue(v));
        v = rs.getString("buyer_preferred_condition");
        if (v != null) b.setBuyerPreferredCondition(CarCondition.fromValue(v));
        v = rs.getString("buyer_preferred_completion");
        if (v != null) b.setBuyerPreferredCompletion(CarCompletion.fromValue(v));
        v = rs.getString("buyer_preferred_color");
        if (v != null) b.setBuyerPreferredColor(CarColor.fromValue(v));

        Date d = rs.getDate("buyer_car_release_date");
        if (d != null) b.setBuyerCarReleaseDate(d.toLocalDate());

        int m = rs.getInt("buyer_max_mileage");
        if (!rs.wasNull()) b.setBuyerMaxMileage(m);

        BigDecimal p = rs.getBigDecimal("buyer_max_price");
        if (!rs.wasNull()) b.setBuyerMaxPrice(p);

        return b;
    }

    private void setParams(PreparedStatement stmt, Buyer b) throws SQLException {
        stmt.setString(1, b.getBuyerSurname());
        stmt.setString(2, b.getBuyerName());
        stmt.setString(4, b.getBuyerTel());

        setStringOrNull(stmt,3, b.getBuyerPatronymic());
        setStringOrNull(stmt,5, b.getBuyerPreferredBrand());
        setStringOrNull(stmt,6, b.getBuyerPreferredModel());

        setEnum(stmt, 7, b.getBuyerPreferredBodyType());
        setEnum(stmt, 8, b.getBuyerPreferredWheelDrive());
        setEnum(stmt, 9, b.getBuyerPreferredGearbox());
        setEnum(stmt, 10, b.getBuyerPreferredCondition());
        setEnum(stmt, 11, b.getBuyerPreferredCompletion());
        setEnum(stmt, 12, b.getBuyerPreferredColor());

        setDate(stmt, 13, b.getBuyerCarReleaseDate());
        setIntOrNull(stmt, 14, b.getBuyerMaxMileage());
        setBigDecimalOrNull(stmt, 15, b.getBuyerMaxPrice());
    }

    private void setEnum(PreparedStatement stmt, int idx, Enum<?> e) throws SQLException {
        if (e != null) {
            try {
                String val = (String) e.getClass().getMethod("getValue").invoke(e);
                stmt.setString(idx, val);
            } catch (Exception ex) {
                stmt.setString(idx, e.name());
            }
        } else {
            stmt.setNull(idx, Types.VARCHAR);
        }
    }

    private void setDate(PreparedStatement stmt, int idx, LocalDate d) throws SQLException {
        if (d != null) stmt.setDate(idx, Date.valueOf(d));
        else stmt.setNull(idx, Types.DATE);
    }

    private void setStringOrNull(PreparedStatement stmt, int idx, String v) throws SQLException {
        if (v != null) stmt.setString(idx, v);
        else stmt.setNull(idx, Types.VARCHAR);
    }

    private void setIntOrNull(PreparedStatement stmt, int idx, Integer v) throws SQLException {
        if (v != null) stmt.setInt(idx, v);
        else stmt.setNull(idx, Types.INTEGER);
    }

    private void setBigDecimalOrNull(PreparedStatement stmt, int idx, BigDecimal v) throws SQLException {
        if (v != null) stmt.setBigDecimal(idx, v);
        else stmt.setNull(idx, Types.DECIMAL);
    }
}