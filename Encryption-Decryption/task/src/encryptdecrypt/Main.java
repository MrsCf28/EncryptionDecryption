package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String mode = "enc";
        int key = 0;
        StringBuilder data = new StringBuilder();
        File outputFile = null;
        boolean output = false;
        String alg = "shift";

        for (int i = 0; i < args.length; i += 2) {
            if ("-mode".equals(args[i])) {
                mode = args[i + 1];
            }
            if ("-key".equals(args[i])) {
                key = Integer.parseInt(args[i + 1]);
            }
            if ("-data".equals(args[i])) {
                data = new StringBuilder(args[i + 1]);
            } else if ("-in".equals(args[i])) {
                String filePath = args[i + 1];
                File file = new File(filePath);
                try (Scanner scanner = new Scanner(file)) {
                    while (scanner.hasNext()) {
                        data.append(scanner.nextLine());
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Error: No file found - " + filePath);
                }
            }
            if ("-out".equals(args[i])) {
                outputFile = new File(args[i + 1]);
                output = true;
            }
            if ("-alg".equals(args[i])) {
                alg = args[i + 1];
            }
        }

        ContextEncryptDecrypt coder;
        coder = new ContextEncryptDecrypt();
        EncryptorDecryptor algorithm;
        if ("unicode".equals(alg)) {
            algorithm = new UnicodeAlgorithm();
        } else {
            algorithm = new ShiftAlgorithm();
        }
        coder.setEncryptorDecryptor(algorithm);

        String result = algorithm.crypt(mode, key, data.toString());
        
        if (output) {
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(result);
            } catch (IOException e) {
                System.out.printf("An exception occurred %s", e.getMessage());
            }
        } else {
            System.out.println(result);
        }
    }
}

class ContextEncryptDecrypt {

    private EncryptorDecryptor algorithm;

    public void setEncryptorDecryptor(EncryptorDecryptor algorithm) {
        this.algorithm = algorithm;
    }

    public String crypt(String mode, int key, String data) {
        return this.algorithm.crypt(mode, key, data);
    }
}

interface EncryptorDecryptor {

    String crypt(String mode, int key, String data);
}

class UnicodeAlgorithm implements EncryptorDecryptor {

    @Override
    public String crypt(String mode, int key, String data) {

        StringBuilder encryptedMessage = new StringBuilder();
        if ("dec".equals(mode)) {
            key *= -1;
        }

        for (char a : data.toCharArray()) {
            char b = (char) (a + key);
            encryptedMessage.append(b);
        }
        return String.valueOf(encryptedMessage);
    }
}

class ShiftAlgorithm implements EncryptorDecryptor {

    @Override
    public String crypt(String mode, int key, String data) {

        if ("dec".equals(mode)) {
            key = 26 - key;
        }

        StringBuilder encryptedMessage = new StringBuilder();
        char b;
        for (char a : data.toCharArray()) {
            if (a > 64 && a < 91) {
                if (a + key > 90) {
                    b = (char) ((a + key) % 90 + 64);
                } else {
                    b = (char) (a + key);
                }
            } else if (a > 96 && a < 123) {
                if (a + key > 122) {
                    b = (char) ((a + key) % 122 + 96);
                } else {
                    b = (char) (a + key);
                }
            } else if (a < 97 && a > 91 || a < 65) {
                b = a;
            } else {
                return "Unknown Character";
        }
        encryptedMessage.append(b);
        }
        return String.valueOf(encryptedMessage);
    }
}
