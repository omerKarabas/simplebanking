package com.eteration.simplebanking.util;

/**
 * Fintech uygulamaları için güvenli maskeleme utility sınıfı.
 * Hassas verilerin loglanmasında kullanılır.
 */
public final class MaskUtil {
    
    private static final String DEFAULT_MASK = "****";
    private static final String PHONE_MASK = "*****";
    private static final String APPROVAL_MASK = "****";
    
    private MaskUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Hesap numarasını maskeleyer (son 4 hane görünür)
     * @param accountNumber hesap numarası
     * @return maskelenmiş hesap numarası
     */
    public static String maskAccount(String accountNumber) {
        if (StringUtil.isBlank(accountNumber) || accountNumber.length() < 4) {
            return DEFAULT_MASK;
        }
        return DEFAULT_MASK + accountNumber.substring(accountNumber.length() - 4);
    }
    
    /**
     * Telefon numarasını maskeleyer (son 4 hane görünür)
     * @param phone telefon numarası
     * @return maskelenmiş telefon numarası
     */
    public static String maskPhone(String phone) {
        if (StringUtil.isBlank(phone) || phone.length() < 4) {
            return PHONE_MASK;
        }
        return PHONE_MASK + phone.substring(phone.length() - 4);
    }
    
    /**
     * Onay kodunu maskeleyer (son 4 hane görünür)
     * @param approvalCode onay kodu
     * @return maskelenmiş onay kodu
     */
    public static String maskApprovalCode(String approvalCode) {
        if (StringUtil.isBlank(approvalCode) || approvalCode.length() < 4) {
            return APPROVAL_MASK;
        }
        return APPROVAL_MASK + approvalCode.substring(approvalCode.length() - 4);
    }
    
    /**
     * İsmi maskeleyer (baş harf + nokta)
     * @param name isim
     * @return maskelenmiş isim
     */
    public static String maskName(String name) {
        if (StringUtil.isBlank(name)) {
            return "-";
        }
        return name.charAt(0) + ".";
    }
    
    /**
     * Alacaklı ismini maskeleyer (baş harf + nokta)
     * @param payee alacaklı ismi
     * @return maskelenmiş alacaklı ismi
     */
    public static String maskPayee(String payee) {
        if (StringUtil.isBlank(payee)) {
            return "-";
        }
        return payee.charAt(0) + ".";
    }
} 