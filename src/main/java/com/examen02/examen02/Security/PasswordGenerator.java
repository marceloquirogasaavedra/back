package com.examen02.examen02.Security;

import java.security.SecureRandom;
public class PasswordGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_";
    private static final int PASSWORD_LENGTH = 12; // Longitud de la contraseña (puedes cambiarlo según tus necesidades)
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }

    public static void main(String[] args) {
        String randomPassword = generateRandomPassword(PASSWORD_LENGTH);
        System.out.println("Contraseña generada: " + randomPassword);
    }
}
