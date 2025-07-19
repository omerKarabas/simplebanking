package com.eteration.simplebanking.domain.entity.transaction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.domain.entity.BankAccount;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("DEPOSIT")
public class DepositTransaction extends Transaction {
	
	@Override
	public void execute(BankAccount account) throws InsufficientBalanceException {
		account.setBalance(account.getBalance() + amount);
	}
} 