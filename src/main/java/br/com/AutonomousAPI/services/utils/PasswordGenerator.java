package br.com.AutonomousAPI.services.utils;

import java.security.SecureRandom;

public class PasswordGenerator {
    private static String characters = "0123456789";
    private static int length = 8;

    public static String generateDefaultPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}
