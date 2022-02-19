package com.tgilonis.cqrsbank.command.aggregate;


import com.tgilonis.cqrsbank.command.command.CreateAccountCommand;
import com.tgilonis.cqrsbank.command.command.DepositMoneyCommand;
import com.tgilonis.cqrsbank.command.command.WithdrawMoneyCommand;
import com.tgilonis.cqrsbank.common.event.AccountActivatedEvent;
import com.tgilonis.cqrsbank.common.event.AccountCreatedEvent;
import com.tgilonis.cqrsbank.common.event.AccountCreditedEvent;
import com.tgilonis.cqrsbank.common.event.AccountDebitedEvent;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.axonframework.test.matchers.Matchers.*;

class AccountAggregateTest
{
    private FixtureConfiguration<AccountAggregate> fixture;

    @BeforeEach
    void setUp()
    {
        fixture = new AggregateTestFixture<>(AccountAggregate.class);
    }

    @Test
    void onCreateAccountCommand()
    {
        fixture.givenNoPriorActivity()
                .when(new CreateAccountCommand("id", BigDecimal.valueOf(10000)))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(
                        exactSequenceOf(
                        equalTo(new AccountCreatedEvent("id", BigDecimal.valueOf(10000))),
                        equalTo(new AccountActivatedEvent("id", "ACTIVATED"))
                        )
                    )
                );
    }

    @Test
    void onDepositMoneyCommand()
    {
        fixture.given(new AccountCreatedEvent("id", BigDecimal.valueOf(10000)), new AccountActivatedEvent("id", "ACTIVATED"))
                .when(new DepositMoneyCommand("id", BigDecimal.valueOf(5000)))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new AccountCreditedEvent("id", BigDecimal.valueOf(5000)));
    }

    @Test
    public void depositMoneyOnNonexistentAccount() {
        fixture.given()
                .when(new DepositMoneyCommand("id", BigDecimal.valueOf(5000)))
                .expectException(AggregateNotFoundException.class);
    }

    @Test
    void onWithdrawMoneyCommand()
    {
        fixture.given(new AccountCreatedEvent("id", BigDecimal.valueOf(10000)), new AccountActivatedEvent("id", "ACTIVATED"))
                .when(new WithdrawMoneyCommand("id", BigDecimal.valueOf(5000)))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new AccountDebitedEvent("id", BigDecimal.valueOf(5000)));
    }
}