package com.eteration.simplebanking.util;

/**
 * String utility sınıfı - Validation ve temizleme işlemleri için kullanılır
 */
public class StringUtil {
    /**
     * String'in boş, null veya sadece boşluk içerip içermediğini kontrol eder
     * @param str kontrol edilecek string
     * @return boş ise true
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
} 