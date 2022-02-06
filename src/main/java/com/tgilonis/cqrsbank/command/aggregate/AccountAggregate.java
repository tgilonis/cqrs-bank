package com.tgilonis.cqrsbank.command.aggregate;

import java.math.BigDecimal;

public class AccountAggregate
{
    private String accountId;
    private BigDecimal balance;
    private String status;

    public AccountAggregate()
    {
    }
}
