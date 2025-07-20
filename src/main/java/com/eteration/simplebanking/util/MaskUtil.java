package com.eteration.simplebanking.util;

import com.eteration.simplebanking.constant.LogConstants;

public final class MaskUtil {
    
    private MaskUtil() {
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
} 