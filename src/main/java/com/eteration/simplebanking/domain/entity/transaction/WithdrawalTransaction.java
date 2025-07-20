package com.eteration.simplebanking.domain.entity.transaction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.exception.cosntant.MessageKeys;
import com.eteration.simplebanking.domain.entity.BankAccount;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@DiscriminatorValue("WITHDRAWAL")
public class WithdrawalTransaction extends Transaction {
	
	@Override
	public void execute(BankAccount account) throws InsufficientBalanceException {
		if (account.getBalance() < amount) {
			throw new InsufficientBalanceException(MessageKeys.ERROR_INSUFFICIENT_BALANCE);
		}
		account.setBalance(account.getBalance() - amount);
	}
} 