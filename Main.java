package org.example;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class Main {
    static PublicKey ClavePublica;
    static PrivateKey ClavePrivada;
    static String TextoVigenere = "Soy un texto cifrado en Vigenere";
    static String TextoEncriptado;
    static String TextoDesencriptado;

    public static void main(String[] args) {
        /*
        original: https://www.baeldung.com/java-rsa
        */
        try {
            KeyPairGenerator generadorClaves = KeyPairGenerator.getInstance("RSA");
            generadorClaves.initialize(2048);
            KeyPair par = generadorClaves.generateKeyPair();

            ClavePrivada = par.getPrivate();
            ClavePublica = par.getPublic();

            System.out.println("Texto original: "+TextoVigenere);

            TextoEncriptado = encriptar();
            TextoDesencriptado = desencriptar();

            System.out.println("Texto desencriptado: "+TextoDesencriptado);
        } catch (Exception unError) {

        }

    }

    public static String encriptar() {
        try {
            Cipher encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, ClavePublica);

            byte[] secretMessageBytes = TextoVigenere.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);

            String encoded = Base64.getEncoder().encodeToString(encryptedMessageBytes);
            System.out.println("Texto encriptado: "+encoded);
            return encoded;
        } catch (Exception unError) {
            return "";
        }
    }

    public static String desencriptar() {
        try {
            Cipher decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, ClavePrivada);

            byte[] bytesTraducidos = Base64.getDecoder().decode(TextoEncriptado);
            byte[] decryptedMessageBytes = decryptCipher.doFinal(bytesTraducidos);
            String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);

            return decryptedMessage;
        } catch (Exception unError) {
            return "";
        }
    }
}