package carshowroom.model.enums;

public enum OrderStatus {
    PENDING("Ожидает"),
    CONFIRMED("Подтверждён"),
    COMPLETED("Завершён"),
    CANCELLED("Отменён");

    private final String value;

    OrderStatus(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }

    public static OrderStatus fromValue(String v) {
        for (OrderStatus t : values()) if (t.value.equals(v)) return t;
        throw new IllegalArgumentException("Неизвестный статус: " + v);
    }
}
