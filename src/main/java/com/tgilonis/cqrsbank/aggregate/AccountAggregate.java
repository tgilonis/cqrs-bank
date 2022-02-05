package com.tgilonis.cqrsbank.aggregate;

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
