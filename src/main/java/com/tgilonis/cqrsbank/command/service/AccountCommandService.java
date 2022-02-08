package com.tgilonis.cqrsbank.command.service;

import com.tgilonis.cqrsbank.command.command.CreateAccountCommand;
import com.tgilonis.cqrsbank.command.command.DepositMoneyCommand;
import com.tgilonis.cqrsbank.command.command.WithdrawMoneyCommand;
import com.tgilonis.cqrsbank.command.dto.CreateAccountRequest;
import com.tgilonis.cqrsbank.command.dto.DepositRequest;
import com.tgilonis.cqrsbank.command.dto.WithdrawalRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountCommandService
{
    private final CommandGateway commandGateway;

    public AccountCommandService(CommandGateway commandGateway)
    {
        this.commandGateway = commandGateway;
    }

    /**
     * Sends a CreateAccountCommand to the CommandGateway instructing a new account of a given balance to be created.
     * @param createAccountRequest a request containing the starting balance of the account.
     * @return the randomly generated id of the account.
     */
    public CompletableFuture<String> createAccount(CreateAccountRequest createAccountRequest)
    {
        return commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                createAccountRequest.getStartingBalance()
        ));
    }

    /**
     * Sends a DepositMoneyCommand to the CommandGateway instructing the given account id to be debited the given
     * amount.
     * @param depositRequest a request containing the amount to be debited.
     * @return the id of the account.
     */
    public CompletableFuture<String> depositIntoAccount(DepositRequest depositRequest)
    {
        return commandGateway.send(new DepositMoneyCommand(
                depositRequest.getAccountId(),
                depositRequest.getAmount()
        ));
    }

    /**
     * Sends a WithdrawMoneyCommand to the CommandGateway instructing the given account id to be credited the given
     * amount.
     * @param withdrawalRequest a request containing the amount to be credited.
     * @return the id of the account.
     */
    public CompletableFuture<String> withdrawFromAccount(WithdrawalRequest withdrawalRequest)
    {
        return commandGateway.send(new WithdrawMoneyCommand(
                withdrawalRequest.getAccountId(),
                withdrawalRequest.getAmount()
        ));
    }
}
