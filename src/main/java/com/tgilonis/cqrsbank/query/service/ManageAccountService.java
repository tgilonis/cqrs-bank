package com.tgilonis.cqrsbank.query.service;

import com.tgilonis.cqrsbank.command.aggregate.AccountAggregate;
import com.tgilonis.cqrsbank.common.event.AccountActivatedEvent;
import com.tgilonis.cqrsbank.common.event.AccountCreatedEvent;
import com.tgilonis.cqrsbank.common.event.AccountCreditedEvent;
import com.tgilonis.cqrsbank.common.event.AccountDebitedEvent;
import com.tgilonis.cqrsbank.query.entity.Account;
import com.tgilonis.cqrsbank.query.query.FindAccountByIdQuery;
import com.tgilonis.cqrsbank.query.repository.AccountRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public class ManageAccountService
{
    private final AccountRepository accountRepository;

    public ManageAccountService(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent)
    {
        Account account = new Account();
        account.setAccountId(accountCreatedEvent.getId());
        account.setBalance(accountCreatedEvent.getBalance());
        account.setStatus("CREATED");

        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent accountActivatedEvent)
    {
        Account account = accountRepository.findById(accountActivatedEvent.getId()).orElse(null);

        if(account != null)
        {
            account.setStatus(accountActivatedEvent.getStatus());
            accountRepository.save(account);
        }
    }

    @EventHandler
    public void on(AccountCreditedEvent accountCreditedEvent)
    {
        Account account = accountRepository.findById(accountCreditedEvent.getId()).orElse(null);

        if(account != null)
        {
            account.setBalance(account.getBalance().add(accountCreditedEvent.getAmount()));
            accountRepository.save(account);
        }
    }

    @EventHandler
    public void on(AccountDebitedEvent accountDebitedEvent)
    {
        Account account = accountRepository.findById(accountDebitedEvent.getId()).orElse(null);

        if(account != null)
        {
            account.setBalance(account.getBalance().subtract(accountDebitedEvent.getAmount()));
            accountRepository.save(account);
        }
    }

    @QueryHandler
    public Account handle(FindAccountByIdQuery query)
    {
        return accountRepository.findById(query.getAccountId()).orElse(null);
    }


}
