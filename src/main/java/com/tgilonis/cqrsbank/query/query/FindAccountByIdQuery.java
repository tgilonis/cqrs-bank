package com.tgilonis.cqrsbank.query.query;

public class FindAccountByIdQuery
{
    private String accountId;

    public FindAccountByIdQuery(String accountId)
    {
        this.accountId = accountId;
    }

    public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }
}
