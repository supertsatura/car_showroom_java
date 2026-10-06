package carshowroom.model.enums;

public enum CarCondition {
    NEW("Новый"),
    USED("Б/У");

    private final String value;

    CarCondition(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }

    public static CarCondition fromValue(String v) {
        for (CarCondition t : values()) if (t.value.equals(v)) return t;
        throw new IllegalArgumentException("Неизвестное состояние: " + v);
    }
}
