package com.eteration.simplebanking.domain.entity.transaction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.exception.cosntant.MessageKeys;
import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.enums.PhoneCompany;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("PHONE_BILL_PAYMENT")
public class PhoneBillPaymentTransaction extends Transaction {
	
	@Enumerated(EnumType.STRING)
	@Column(name = "phone_company")
	private PhoneCompany phoneCompany;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	public PhoneBillPaymentTransaction(PhoneCompany phoneCompany, String phoneNumber, double amount) {
		this.phoneCompany = phoneCompany;
		this.phoneNumber = phoneNumber;
		this.amount = amount;
		this.date = LocalDateTime.now();
	}
	
	@Override
	public void execute(BankAccount account) throws InsufficientBalanceException {
		if (account.getBalance() < amount) {
			throw new InsufficientBalanceException(MessageKeys.INSUFFICIENT_BALANCE_FOR_PHONE_BILL);
		}
		account.setBalance(account.getBalance() - amount);
	}
} 