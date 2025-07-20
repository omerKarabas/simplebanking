package com.eteration.simplebanking.util;

import com.eteration.simplebanking.model.dto.SecurityConfig;
import com.eteration.simplebanking.domain.constant.LogConstants;
import com.eteration.simplebanking.exception.cosntant.MessageKeys;
import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class SecureMaskUtil {
    
    private final SecurityConfig securityConfig;
    
    public SecureMaskUtil(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
        
        if (securityConfig.algorithm() == null || securityConfig.secretKey() == null) {
            throw new IllegalStateException("Security configuration is incomplete. SECURITY_ALGORITHM and SECURITY_SECRET_KEY environment variables must be set.");
        }
    }
    
    public String maskAccount(String accountNumber) {
        if (StringUtil.isBlank(accountNumber) || accountNumber.length() < 4) {
            return LogConstants.DEFAULT_MASK.getValue();
        }
        return LogConstants.DEFAULT_MASK.getValue() + accountNumber.substring(accountNumber.length() - 4);
    }
    
    public String maskPhone(String phone) {
        if (StringUtil.isBlank(phone) || phone.length() < 4) {
            return LogConstants.PHONE_MASK.getValue();
        }
        return LogConstants.PHONE_MASK.getValue() + phone.substring(phone.length() - 4);
    }
    
    public String maskApprovalCode(String approvalCode) {
        if (StringUtil.isBlank(approvalCode) || approvalCode.length() < 4) {
            return LogConstants.APPROVAL_MASK.getValue();
        }
        return LogConstants.APPROVAL_MASK.getValue() + approvalCode.substring(approvalCode.length() - 4);
    }
    
    public String maskName(String name) {
        if (StringUtil.isBlank(name)) {
            return LogConstants.EMPTY_VALUE.getValue();
        }
        return name.charAt(0) + LogConstants.NAME_SUFFIX.getValue();
    }
    
    public String maskPayee(String payee) {
        if (StringUtil.isBlank(payee)) {
            return LogConstants.EMPTY_VALUE.getValue();
        }
        return payee.charAt(0) + LogConstants.NAME_SUFFIX.getValue();
    }
    
    public String encryptAccount(String accountNumber) {
        if (StringUtil.isBlank(accountNumber)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(securityConfig.algorithm());
            cipher.init(Cipher.ENCRYPT_MODE, securityConfig.secretKeySpec());
            byte[] encryptedBytes = cipher.doFinal(accountNumber.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(MessageKeys.ERROR_ENCRYPTION_FAILED.getKey(), e);
        }
    }
    
    public String decryptAccount(String encryptedAccount) {
        if (StringUtil.isBlank(encryptedAccount)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(securityConfig.algorithm());
            cipher.init(Cipher.DECRYPT_MODE, securityConfig.secretKeySpec());
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedAccount));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(MessageKeys.ERROR_DECRYPTION_FAILED.getKey(), e);
        }
    }
    
    public String encryptPhone(String phone) {
        if (StringUtil.isBlank(phone)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(securityConfig.algorithm());
            cipher.init(Cipher.ENCRYPT_MODE, securityConfig.secretKeySpec());
            byte[] encryptedBytes = cipher.doFinal(phone.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(MessageKeys.ERROR_ENCRYPTION_FAILED.getKey(), e);
        }
    }
    
    public String decryptPhone(String encryptedPhone) {
        if (StringUtil.isBlank(encryptedPhone)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(securityConfig.algorithm());
            cipher.init(Cipher.DECRYPT_MODE, securityConfig.secretKeySpec());
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPhone));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(MessageKeys.ERROR_DECRYPTION_FAILED.getKey(), e);
        }
    }
    
    public String encryptApprovalCode(String approvalCode) {
        if (StringUtil.isBlank(approvalCode)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(securityConfig.algorithm());
            cipher.init(Cipher.ENCRYPT_MODE, securityConfig.secretKeySpec());
            byte[] encryptedBytes = cipher.doFinal(approvalCode.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(MessageKeys.ERROR_ENCRYPTION_FAILED.getKey(), e);
        }
    }
    
    public String decryptApprovalCode(String encryptedApprovalCode) {
        if (StringUtil.isBlank(encryptedApprovalCode)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(securityConfig.algorithm());
            cipher.init(Cipher.DECRYPT_MODE, securityConfig.secretKeySpec());
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedApprovalCode));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(MessageKeys.ERROR_DECRYPTION_FAILED.getKey(), e);
        }
    }
} 