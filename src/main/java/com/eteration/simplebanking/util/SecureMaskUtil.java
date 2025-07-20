package com.eteration.simplebanking.util;

import com.eteration.simplebanking.constant.LogConstants;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public final class SecureMaskUtil {
    
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "SimpleBanking2024!";
    private static final SecretKeySpec SECRET_KEY_SPEC = new SecretKeySpec(
            SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
    
    private SecureMaskUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    public static String maskAccount(String accountNumber) {
        if (StringUtil.isBlank(accountNumber) || accountNumber.length() < 4) {
            return LogConstants.DEFAULT_MASK.getValue();
        }
        return LogConstants.DEFAULT_MASK.getValue() + accountNumber.substring(accountNumber.length() - 4);
    }
    
    public static String maskPhone(String phone) {
        if (StringUtil.isBlank(phone) || phone.length() < 4) {
            return LogConstants.PHONE_MASK.getValue();
        }
        return LogConstants.PHONE_MASK.getValue() + phone.substring(phone.length() - 4);
    }
    
    public static String maskApprovalCode(String approvalCode) {
        if (StringUtil.isBlank(approvalCode) || approvalCode.length() < 4) {
            return LogConstants.APPROVAL_MASK.getValue();
        }
        return LogConstants.APPROVAL_MASK.getValue() + approvalCode.substring(approvalCode.length() - 4);
    }
    
    public static String maskName(String name) {
        if (StringUtil.isBlank(name)) {
            return LogConstants.EMPTY_VALUE.getValue();
        }
        return name.charAt(0) + LogConstants.NAME_SUFFIX.getValue();
    }
    
    public static String maskPayee(String payee) {
        if (StringUtil.isBlank(payee)) {
            return LogConstants.EMPTY_VALUE.getValue();
        }
        return payee.charAt(0) + LogConstants.NAME_SUFFIX.getValue();
    }
    
    public static String encryptAccount(String accountNumber) {
        if (StringUtil.isBlank(accountNumber)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY_SPEC);
            byte[] encryptedBytes = cipher.doFinal(accountNumber.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }
    
    public static String decryptAccount(String encryptedAccount) {
        if (StringUtil.isBlank(encryptedAccount)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY_SPEC);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedAccount));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
    
    public static String encryptPhone(String phone) {
        if (StringUtil.isBlank(phone)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY_SPEC);
            byte[] encryptedBytes = cipher.doFinal(phone.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }
    
    public static String decryptPhone(String encryptedPhone) {
        if (StringUtil.isBlank(encryptedPhone)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY_SPEC);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPhone));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
    
    public static String encryptApprovalCode(String approvalCode) {
        if (StringUtil.isBlank(approvalCode)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY_SPEC);
            byte[] encryptedBytes = cipher.doFinal(approvalCode.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }
    
    public static String decryptApprovalCode(String encryptedApprovalCode) {
        if (StringUtil.isBlank(encryptedApprovalCode)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY_SPEC);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedApprovalCode));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
} 