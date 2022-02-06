package com.tgilonis.cqrsbank.query.service;

import com.tgilonis.cqrsbank.query.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class ManageAccountService
{
    private final AccountRepository accountRepository;

    public ManageAccountService(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
    }


}
