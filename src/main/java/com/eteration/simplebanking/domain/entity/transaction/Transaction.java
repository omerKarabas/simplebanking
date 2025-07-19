package com.eteration.simplebanking.domain.entity.transaction;

import com.eteration.simplebanking.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.domain.entity.BankAccount;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction extends BaseEntity {
	
	@NotNull(message = "Amount cannot be null")
	@DecimalMin(value = "0.01", message = "Amount must be greater than 0")
	@Column(name = "amount", nullable = false)
	protected double amount;
	
	@NotNull(message = "Date cannot be null")
	@Column(name = "date", nullable = false)
	protected LocalDateTime date;
	
	@NotNull(message = "Account cannot be null")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	private BankAccount account;
	
	public abstract void execute(BankAccount account) throws InsufficientBalanceException;
} 