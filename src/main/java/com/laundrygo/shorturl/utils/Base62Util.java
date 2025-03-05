package com.laundrygo.shorturl.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Base62Util {

    private static final int SHORT_URL_LENGTH = 8;
    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateShortUrlFromUuid() {
        String uuidHex = UUID.randomUUID().toString().replace("-", "");
        return encodeBase62(uuidHex);
    }

    private String encodeBase62(String hexString) {
        StringBuilder encoded = new StringBuilder();

        for (char hexChar : hexString.toCharArray()) {
            int value = Character.digit(hexChar, 16);
            encoded.append(BASE62_CHARS.charAt(value % 62));
        }

        return encoded.substring(0, Math.min(SHORT_URL_LENGTH, encoded.length()));
    }
}