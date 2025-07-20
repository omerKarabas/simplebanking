package com.eteration.simplebanking.domain.entity.transaction;

import com.eteration.simplebanking.domain.entity.BaseEntity;
import com.eteration.simplebanking.domain.validation.annotation.PositiveAmount;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.domain.entity.BankAccount;

import jakarta.persistence.*;
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
	
	@NotNull(message = "{validation.transaction.amount.required}")
	@PositiveAmount
	@Column(name = "amount", nullable = false)
	protected double amount;
	
	@NotNull(message = "{validation.transaction.date.required}")
	@Column(name = "date", nullable = false)
	protected LocalDateTime date;
	
	@NotNull(message = "{validation.transaction.account.required}")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	private BankAccount account;
	
	@NotNull(message = "{validation.transaction.approval.code.required}")
	@Column(name = "approval_code", nullable = false, unique = true)
	private String approvalCode;
	
	public abstract void execute(BankAccount account) throws InsufficientBalanceException;
} 