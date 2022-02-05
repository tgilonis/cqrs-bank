package com.tgilonis.cqrsbank.command.dto;

import java.math.BigDecimal;

public class CreateAccountRequest
{
    private BigDecimal startingBalance;

    public CreateAccountRequest(BigDecimal startingBalance)
    {
        this.startingBalance = startingBalance;
    }

    public CreateAccountRequest()
    {
    }

    @Override
    public String toString()
    {
        return "CreateAccountRequest{" +
                "startingBalance=" + startingBalance +
                '}';
    }

    public BigDecimal getStartingBalance()
    {
        return startingBalance;
    }

    public void setStartingBalance(BigDecimal startingBalance)
    {
        this.startingBalance = startingBalance;
    }
}
