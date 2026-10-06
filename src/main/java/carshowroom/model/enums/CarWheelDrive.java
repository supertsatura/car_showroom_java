package carshowroom.model.enums;


public enum CarWheelDrive {
    FWD("Передний"),
    RWD("Задний"),
    AWD("Полный");

    private final String value;

    CarWheelDrive(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }

    public static CarWheelDrive fromValue(String v) {
        for (CarWheelDrive t : values()) if (t.value.equals(v)) return t;
        throw new IllegalArgumentException("Неизвестный привод: " + v);
    }
}