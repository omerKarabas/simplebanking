package com.eteration.simplebanking.util;

import com.eteration.simplebanking.model.dto.SecurityConfig;
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
    
    private String encrypt(String data) {
        if (StringUtil.isBlank(data)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, securityConfig.secretKeySpec());
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(MessageKeys.ERROR_ENCRYPTION_FAILED.getKey(), e);
        }
    }
    
    private String decrypt(String encryptedData) {
        if (StringUtil.isBlank(encryptedData)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, securityConfig.secretKeySpec());
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(MessageKeys.ERROR_DECRYPTION_FAILED.getKey(), e);
        }
    }
    
    public String encryptAccount(String accountNumber) {
        return encrypt(accountNumber);
    }
    
    public String decryptAccount(String encryptedAccount) {
        return decrypt(encryptedAccount);
    }
    
    public String encryptPhone(String phone) {
        return encrypt(phone);
    }
    
    public String decryptPhone(String encryptedPhone) {
        return decrypt(encryptedPhone);
    }
    
    public String encryptApprovalCode(String approvalCode) {
        return encrypt(approvalCode);
    }
    
    public String decryptApprovalCode(String encryptedApprovalCode) {
        return decrypt(encryptedApprovalCode);
    }
    
    public String encryptName(String name) {
        return encrypt(name);
    }
    
    public String decryptName(String encryptedName) {
        return decrypt(encryptedName);
    }
    
    public String encryptPayee(String payee) {
        return encrypt(payee);
    }
    
    public String decryptPayee(String encryptedPayee) {
        return decrypt(encryptedPayee);
    }
} 