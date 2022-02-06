package com.tgilonis.cqrsbank.command.service;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandService
{
    private final CommandGateway commandGateway;

    public AccountCommandService(CommandGateway commandGateway)
    {
        this.commandGateway = commandGateway;
    }
}
