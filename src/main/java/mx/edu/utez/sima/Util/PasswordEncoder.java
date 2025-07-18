package mx.edu.utez.sima.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    
    public static String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static boolean verifyPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
