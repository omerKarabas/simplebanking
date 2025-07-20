package com.eteration.simplebanking.service;

import com.eteration.simplebanking.util.SecureMaskUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecureLoggingService {
    
    public void logTransaction(String accountNumber, String phoneNumber, String approvalCode, double amount) {
        String maskedAccount = SecureMaskUtil.maskAccount(accountNumber);
        String maskedPhone = SecureMaskUtil.maskPhone(phoneNumber);
        String maskedApprovalCode = SecureMaskUtil.maskApprovalCode(approvalCode);
        
        log.debug("[TRANSACTION] Account: {}, Phone: {}, ApprovalCode: {}, Amount: {}", 
                maskedAccount, maskedPhone, maskedApprovalCode, amount);
    }
    
    public void logEncryptedTransaction(String accountNumber, String phoneNumber, String approvalCode, double amount) {
        String encryptedAccount = SecureMaskUtil.encryptAccount(accountNumber);
        String encryptedPhone = SecureMaskUtil.encryptPhone(phoneNumber);
        String encryptedApprovalCode = SecureMaskUtil.encryptApprovalCode(approvalCode);
        
        log.debug("[ENCRYPTED_TRANSACTION] Account: {}, Phone: {}, ApprovalCode: {}, Amount: {}", 
                encryptedAccount, encryptedPhone, encryptedApprovalCode, amount);
    }
    
    public void logAuditTrail(String accountNumber, String phoneNumber, String approvalCode) {
        String maskedAccount = SecureMaskUtil.maskAccount(accountNumber);
        String encryptedAccount = SecureMaskUtil.encryptAccount(accountNumber);
        
        log.debug("[AUDIT] MaskedAccount: {}, EncryptedAccount: {}, Phone: {}, ApprovalCode: {}", 
                maskedAccount, encryptedAccount, SecureMaskUtil.maskPhone(phoneNumber), 
                SecureMaskUtil.maskApprovalCode(approvalCode));
    }
    
    public String getDecryptedAccount(String encryptedAccount) {
        return SecureMaskUtil.decryptAccount(encryptedAccount);
    }
    
    public String getDecryptedPhone(String encryptedPhone) {
        return SecureMaskUtil.decryptPhone(encryptedPhone);
    }
    
    public String getDecryptedApprovalCode(String encryptedApprovalCode) {
        return SecureMaskUtil.decryptApprovalCode(encryptedApprovalCode);
    }
} 