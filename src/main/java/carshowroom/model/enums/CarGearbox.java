package carshowroom.model.enums;

public enum CarGearbox {
    AUTOMATIC("АКПП"),
    MANUAL("МКПП");

    private final String value;

    CarGearbox(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }

    public static CarGearbox fromValue(String v) {
        for (CarGearbox t : values()) if (t.value.equals(v)) return t;
        throw new IllegalArgumentException("Неизвестная КПП: " + v);
    }
}