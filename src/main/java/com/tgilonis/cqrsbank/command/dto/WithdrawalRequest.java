package com.tgilonis.cqrsbank.command.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class WithdrawalRequest
{
    private String accountId;
    private BigDecimal amount;

    public WithdrawalRequest(String accountId, BigDecimal amount)
    {
        this.accountId = accountId;
        this.amount = amount;
    }

    public WithdrawalRequest()
    {
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(accountId, amount);
    }

    @Override
    public String toString()
    {
        return "WithdrawalRequest{" +
                "accountId='" + accountId + '\'' +
                ", amount=" + amount +
                '}';
    }

    public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
}
