package carshowroom.model.enums;

public enum CarColor {
    WHITE("Белый"),
    GREY("Серый"),
    BLACK("Черный"),
    BLUE("Синий"),
    RED("Красный"),
    GREEN("Зеленый"),
    YELLOW("Желтый"),
    ORANGE("Оранжевый"),
    VIOLET("Фиолетовый");

    private final String value;

    CarColor(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }

    public static CarColor fromValue(String v) {
        for (CarColor t : values()) if (t.value.equals(v)) return t;
        throw new IllegalArgumentException("Неизвестный цвет: " + v);
    }
}
