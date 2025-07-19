package com.eteration.simplebanking.domain.entity;

import com.eteration.simplebanking.domain.entity.transaction.*;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.domain.enums.PhoneCompany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bank_accounts", indexes = {
    @Index(name = "idx_account_number", columnList = "account_number")
})
public class BankAccount extends BaseEntity {

	@NotNull(message = "Owner cannot be null")
	@Column(name = "owner", nullable = false)
	private String owner;

	@NotNull(message = "Account number cannot be null")
	@Column(name = "account_number", nullable = false, unique = true)
	private String accountNumber;

	@NotNull(message = "Balance cannot be null")
	@DecimalMin(value = "0.0", message = "Balance cannot be negative")
	@Column(name = "balance", nullable = false)
	@Builder.Default
	private double balance = 0.0;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<Transaction> transactions = new ArrayList<>();
	
	public void post(Transaction transaction) throws InsufficientBalanceException {
		transaction.execute(this);
		transaction.setAccount(this);
		this.transactions.add(transaction);
	}
	
	public void credit(double amount) throws InsufficientBalanceException {
		DepositTransaction deposit = new DepositTransaction();
		deposit.setAmount(amount);
		deposit.setDate(LocalDateTime.now());
		post(deposit);
	}
	
	public void debit(double amount) throws InsufficientBalanceException {
		WithdrawalTransaction withdrawal = new WithdrawalTransaction();
		withdrawal.setAmount(amount);
		withdrawal.setDate(LocalDateTime.now());
		post(withdrawal);
	}
	
	public void payPhoneBill(PhoneCompany phoneCompany, String phoneNumber, double amount) throws InsufficientBalanceException {
		PhoneBillPaymentTransaction phoneBill = new PhoneBillPaymentTransaction(phoneCompany, phoneNumber, amount);
		post(phoneBill);
	}
	
	public void payCheck(String payee, double amount) throws InsufficientBalanceException {
		CheckTransaction check = new CheckTransaction(payee, amount);
		post(check);
	}
	

}
