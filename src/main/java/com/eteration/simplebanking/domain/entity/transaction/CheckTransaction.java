package com.eteration.simplebanking.domain.entity.transaction;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("CHECK")
public class CheckTransaction extends Transaction {
	
	@Column(name = "check_number")
	private String checkNumber;
	
	@Column(name = "payee")
	private String payee;
	
	public CheckTransaction(String payee, double amount) {
		this.payee = payee;
		this.amount = amount;
		this.date = LocalDateTime.now();
		this.checkNumber = generateCheckNumber();
	}
	
	private String generateCheckNumber() {
		// TODO: Bu date işlemleri ileride DateUtils sınıfına taşınacak
		// TODO: Sequence yönetimi için CheckNumberService kullanılacak
		// TODO: Format için constant tanımlanacak
		LocalDateTime now = LocalDateTime.now();
		String year = String.valueOf(now.getYear());
		String monthDay = String.format("%02d%02d", now.getMonthValue(), now.getDayOfMonth());

		long timestamp = System.currentTimeMillis();
		int sequence = (int) (timestamp % 10000);
		
		return String.format("CHK-%s-%s-%04d", year, monthDay, sequence);
	}
	
	@Override
	public void execute(BankAccount account) throws InsufficientBalanceException {
		if (account.getBalance() < amount) {
			throw new InsufficientBalanceException("Insufficient balance for check payment");
		}
		account.setBalance(account.getBalance() - amount);
	}

} 