package com.eteration.simplebanking.domain.constant;

/**
 * Validation için regex pattern'leri
 * Temizleme işlemleri artık StringUtil sınıfında yapılıyor
 */
public final class ValidationRegex {
    
    public static final String ACCOUNT_NUMBER_PATTERN = "^\\d{10,16}$";
    
    public static final String PHONE_NUMBER_PATTERN = "^\\d{10,11}$";
    
    private ValidationRegex() {
    }
} 