package com.thomshutt.runbud.security;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class PasswordHasher {

    final MessageDigest digest;
    final Random random;

    public PasswordHasher() {
        try {
            digest = MessageDigest.getInstance("SHA-1");
//            random = SecureRandom.getInstance("SHA1PRNG");
            random = new Random(System.currentTimeMillis());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateSalt() {
        byte[] bSalt = new byte[8];
        random.nextBytes(bSalt);
        return byteToBase64(bSalt);
    }

    public String hash(String password, String salt) throws IOException {
        digest.reset();
        digest.update(base64ToByte(salt));
        byte[] input = digest.digest(password.getBytes("UTF-8"));
        for (int i = 0; i < 10; i++) {
            digest.reset();
            input = digest.digest(input);
        }

        return byteToBase64(input);
    }

    public boolean passwordMatches(String password, String salt, String hash) {
        try {
            return hash(password, salt).equals(hash);
        } catch (IOException e) {
            // TODO: Shouldn't happen, log this.
            return false;
        }
    }

    public static byte[] base64ToByte(String data) throws IOException {
        return DatatypeConverter.parseBase64Binary(data);
    }

    public static String byteToBase64(byte[] data){
        return DatatypeConverter.printBase64Binary(data);
    }

}
