package tech.razymov.restfull.util;

import java.security.SecureRandom;

public class KeyGenerator {
    public static String generateIdempotenceKey(int length) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom RANDOM = new SecureRandom();
        StringBuilder key = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            key.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return key.toString();
    }
}
