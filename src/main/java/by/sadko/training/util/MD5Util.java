package by.sadko.training.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    private static final Logger LOGGER = LogManager.getLogger(MD5Util.class);

    private MD5Util() {
    }

    public static String md5Custom(String input) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(input.getBytes());
            digest = messageDigest.digest();

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Algorithm is not founded");
            e.printStackTrace();
        }

        BigInteger hash = new BigInteger(1, digest);
        String md5Hex = hash.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}
