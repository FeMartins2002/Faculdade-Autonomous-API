package br.com.AutonomousAPI.services.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static String generateHash(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException error) {
            return null;
        }
    }
}
