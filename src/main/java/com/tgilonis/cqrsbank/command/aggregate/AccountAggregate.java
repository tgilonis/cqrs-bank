package com.tgilonis.cqrsbank.command.aggregate;

import com.tgilonis.cqrsbank.command.command.CreateAccountCommand;
import com.tgilonis.cqrsbank.command.command.DepositMoneyCommand;
import com.tgilonis.cqrsbank.command.command.WithdrawMoneyCommand;
import com.tgilonis.cqrsbank.common.event.AccountActivatedEvent;
import com.tgilonis.cqrsbank.common.event.AccountCreatedEvent;
import com.tgilonis.cqrsbank.common.event.AccountCreditedEvent;
import com.tgilonis.cqrsbank.common.event.AccountDebitedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

@Aggregate
public class AccountAggregate
{
    @AggregateIdentifier
    private String accountId;
    private BigDecimal balance;
    private String status;

    public AccountAggregate()
    {
    }

    /**
     * Command handler to hand the CreateAccountCommand.
     * This applies an event on the AccountAggregate (i.e., generates an event) called AccountCreatedEvent.
     * @param createAccountCommand The CreateAccountCommand selected by the user.
     */
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand)
    {
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getBalance()
        ));
    }

    /**
     * Handles the event generated from the AccountCreatedEvent.
     * Sets up the initial account balance and status of ACTIVATED.
     * Generates an AccountActivatedEvent.
     * @param accountCreatedEvent an AccountCreatedEvent generated from the CreateAccountCommand.
     */
    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent)
    {
        this.accountId = accountCreatedEvent.getId();
        this.balance = accountCreatedEvent.getBalance();
        this.status = "CREATED";

        AggregateLifecycle.apply(new AccountActivatedEvent(
                this.accountId,
                "ACTIVATED"
        ));
    }

    /**
     * Handles the event generated from the AccountCreatedEvent.
     * Sets the status of the account to ACTIVE.
     * @param accountActivatedEvent an AccountActivatedEvent generated from the AccountCreatedEvent.
     */
    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent)
    {
        this.status = accountActivatedEvent.getStatus();
    }

    /**
     * CommandHandler to handle the DepositMoneyCommand.
     * This generates the AccountDebitedEvent.
     * @param depositMoneyCommand The DepositMoneyCommand selected by the user.
     */
    @CommandHandler
    public void on(DepositMoneyCommand depositMoneyCommand)
    {
        AggregateLifecycle.apply(new AccountDebitedEvent(
                depositMoneyCommand.getId(),
                depositMoneyCommand.getAmount()
        ));
    }

    /**
     * Handles the event generated from the DepositMoneyCommand.
     * Adds the amount from the event to the account balance.
     * @param accountDebitedEvent an AccountDebitedEvent generated from the DepositMoneyCommand.
     */
    @EventSourcingHandler
    public void on(AccountDebitedEvent accountDebitedEvent)
    {
        this.balance = this.balance.add(accountDebitedEvent.getAmount());
    }

    /**
     * CommandHandler to handle the WithdrawMoneyCommand.
     * This generates the AccountCreditedCommand.
     * @param withdrawMoneyCommand The WithdrawMoneyCommand selected by the user.
     */
    @CommandHandler
    public void on(WithdrawMoneyCommand withdrawMoneyCommand)
    {
        AggregateLifecycle.apply(new AccountCreditedEvent(
                withdrawMoneyCommand.getId(),
                withdrawMoneyCommand.getAmount()
        ));
    }

    /**
     * Handles the event generated from the WithdrawMoneyCommand.
     * Subtracts the amount from the event to the account balance.
     * @param accountCreditedEvent an AccountCreditedEvent generated from the WithdrawMoneyCommand.
     */
    @EventSourcingHandler
    public void on(AccountCreditedEvent accountCreditedEvent)
    {
        this.balance = this.balance.subtract(accountCreditedEvent.getAmount());
    }

}
