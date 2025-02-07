package components.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Метод для хеширования пароля
    public static String hashPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }

    // Метод для проверки пароля
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return encoder.matches(plainPassword, hashedPassword);
    }
}
