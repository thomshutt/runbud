package com.thomshutt.runbud.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHasher {

    final MessageDigest digest;
    final SecureRandom random;

    public PasswordHasher() {
        try {
            digest = MessageDigest.getInstance("SHA-1");
            random = SecureRandom.getInstance("SHA1PRNG");
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
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(data);
    }

    public static String byteToBase64(byte[] data){
        BASE64Encoder endecoder = new BASE64Encoder();
        return endecoder.encode(data);
    }

}
