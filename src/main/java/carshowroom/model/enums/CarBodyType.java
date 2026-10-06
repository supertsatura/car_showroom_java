package carshowroom.model.enums;


public enum CarBodyType {
    HATCHBACK("Хэтчбек"),
    SEDAN("Седан"),
    COUPE("Купе"),
    CABRIOLET("Кабриолет"),
    UNIVERSAL("Универсал"),
    SUV("Внедорожник"),
    CROSSOVER("Кроссовер"),
    VAN("Вэн");

    private final String value;

    CarBodyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CarBodyType fromValue(String v) {
        for (CarBodyType t : values()) if (t.value.equals(v)) return t;
        throw new IllegalArgumentException("Неизвестный тип кузова: " + v);
    }
}
