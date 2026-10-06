package carshowroom.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

public class InputReader {
    private final Scanner scanner;

    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("  ⚠ Ошибка: введено не число. Попробуйте снова.");
            }
        }
    }

    public int readPositiveInt(String prompt) {
        while (true) {
            int val = readInt(prompt);
            if (val > 0) return val;
            System.out.println("  ⚠ Число должно быть положительным.");
        }
    }

    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.println("  ⚠ Ошибка: введено не число. Попробуйте снова.");
            }
        }
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public String readRequiredString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("  ⚠ Поле не может быть пустым.");
        }
    }

    public String readRequiredString(String prompt, String defaultValue) {
        System.out.print(prompt);
        String s = scanner.nextLine().trim();
        return s.isEmpty() ? defaultValue : s;
    }

    public LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt + " (формат ГГГГ-ММ-ДД): ");
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("   Ошибка: неверный формат даты. Используйте ГГГГ-ММ-ДД.");
            }
        }
    }

    public String readPhone(String prompt) {
        while (true) {
            System.out.print(prompt + " (формат XX-XX-XX): ");
            String input = scanner.nextLine().trim();
            if (input.matches("^\\d{2}-\\d{2}-\\d{2}$")) {
                return input;
            }
            System.out.println("  ⚠ Ошибка: телефон должен быть в формате XX-XX-XX (например, 77-77-77).");
        }
    }
}