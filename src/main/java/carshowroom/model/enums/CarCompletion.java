package carshowroom.model.enums;

public enum CarCompletion {
    BASE("Базовая"),
    MEDIUM("Средняя"),
    MAXIMUM("Максимальная");

    private final String value;

    CarCompletion(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }

    public static CarCompletion fromValue(String v) {
        for (CarCompletion t : values()) if (t.value.equals(v)) return t;
        throw new IllegalArgumentException("Неизвестная комплектация: " + v);
    }
}