package com.eteration.simplebanking.util;

public class StringUtil {
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
} 