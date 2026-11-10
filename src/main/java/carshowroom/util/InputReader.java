package carshowroom.util;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Scanner;


public class InputReader {
    private final Scanner scanner;

    public InputReader() {
        this.scanner = new Scanner(System.in);
    }

    public void close() {
        scanner.close();
    }

    public int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            String s = scanner.nextLine().trim();

            try {
                int v = Integer.parseInt(s);

                if (v >= 0) return v;

                System.out.println("  Число должно быть положительным.");
            } catch (NumberFormatException e) {
                System.out.println("  Введено не число.");
            }
        }
    }

    public Integer readOptionalPositiveInt() {
        while (true) {
            System.out.print("  Пробег в км (Enter — пропустить): ");

            String s = scanner.nextLine().trim();

            if (s.isBlank()) return null;

            try {

                int v = Integer.parseInt(s);

                if (v >= 0) return v;

                System.out.println("  Число должно быть положительным.");
            } catch (NumberFormatException e) {
                System.out.println("  Введено не число.");
            }
        }
    }

    public Integer readOptionalPositiveInt(String prompt, Integer defaultValue) {
        while (true) {
            System.out.print(prompt);

            String s = scanner.nextLine().trim();

            if (s.isBlank()) return defaultValue;

            try {

                int v = Integer.parseInt(s);

                if (v >= 0) return v;

                System.out.println("  Число должно быть положительным.");
            } catch (NumberFormatException e) {
                System.out.println("  Введено не число.");
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();

            if (s.isBlank()) return null;

            try {
                return new BigDecimal(s);
            } catch (NumberFormatException e) {
                System.out.println("  Введено не число.");
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt, BigDecimal defaultValue) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();

            if (s.isBlank()) return defaultValue;

            try {
                return new BigDecimal(s);
            } catch (NumberFormatException e) {
                System.out.println("  Введено не число.");
            }
        }
    }

    public String readNonRequiredString(String prompt) {
        System.out.print(prompt);
        String s = scanner.nextLine().trim();

        if (s.isBlank()) return null;

        return s;
    }

    public String readRequiredString(String prompt) {
        while (true) {
            System.out.print(prompt);

            String s = scanner.nextLine().trim();

            if (!s.isBlank()) return s;

            System.out.println("  Поле не может быть пустым.");
        }
    }

    public String readNonRequiredString(String prompt, String defaultValue) {
        System.out.print(prompt);

        String s = scanner.nextLine().trim();

        return s.isBlank() ? defaultValue : s;
    }

    public String readRequiredPhone() {
        while (true) {
            System.out.print("  Телефон (формат XX-XX-XX): ");

            String s = scanner.nextLine().trim();

            if (s.matches("^\\d{2}-\\d{2}-\\d{2}$")) return s;

            System.out.println("  Неверный формат. Пример: 77-77-77");
        }
    }

    public String readNonRequiredPhone(String prompt, String defaultValue) {
        while (true) {
            System.out.print(prompt);

            String s = scanner.nextLine().trim();

            if (s.isBlank()) return defaultValue;
            if (s.matches("^\\d{2}-\\d{2}-\\d{2}$")) return s;

            System.out.println("  Неверный формат. Пример: 77-77-77");
        }
    }

    public LocalDate readDate() {
        while (true) {
            System.out.print("  Дата выпуска (ГГГГ-ММ-ДД): ");

            String s = scanner.nextLine().trim();

            try {
                return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (Exception e) {
                System.out.println("   Неверный формат даты.");
            }
        }
    }

    public LocalDate readNonRequiredDate() {
        while (true) {
            System.out.print("  Дата выпуска (ГГГГ-ММ-ДД): ");

            String s = scanner.nextLine().trim();

            if (s.isBlank()) return null;

            try {
                return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (Exception e) {
                System.out.println("   Неверный формат даты.");
            }
        }
    }

    public LocalDate readNonRequiredDate(String prompt, LocalDate defaultValue) {
        while (true) {
            System.out.print(prompt);

            String s = scanner.nextLine().trim();

            if (s.isBlank()) return defaultValue;

            try {
                return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (Exception e) {
                System.out.println("   Неверный формат даты.");
            }
        }
    }

    public <E extends Enum<E>> E readEnum(String prompt, Class<E> enumClass, boolean isRequired) {
        while (true) {
            System.out.print(prompt);

            String s = scanner.nextLine().trim();

            if (s.isBlank()) {
                if (!isRequired) return null;

                System.out.println("  Поле не может быть пустым. Допустимые: " + Arrays.toString(enumClass.getEnumConstants()));
                continue;
            }

            for (E e : enumClass.getEnumConstants()) {
                try {
                    String val = (String) e.getClass().getMethod("getValue").invoke(e);

                    if (val.equalsIgnoreCase(s)) return e;
                } catch (Exception ex) {
                    if (e.name().equalsIgnoreCase(s)) return e;
                }
            }

            System.out.println("  Введено неверное значение. Допустимые: " + Arrays.toString(enumClass.getEnumConstants()));
        }
    }
}