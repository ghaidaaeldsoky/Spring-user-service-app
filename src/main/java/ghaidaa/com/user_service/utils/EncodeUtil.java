package ghaidaa.com.user_service.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class EncodeUtil {

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); // أو "SHA-512"
            byte[] hashBytes = digest.digest(password.getBytes());
            return HexFormat.of().formatHex(hashBytes); // يحول الـ bytes لـ hex string
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
