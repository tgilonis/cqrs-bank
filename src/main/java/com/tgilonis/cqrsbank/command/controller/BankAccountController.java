package com.tgilonis.cqrsbank.command.controller;

import com.tgilonis.cqrsbank.command.dto.CreateAccountRequest;
import com.tgilonis.cqrsbank.command.dto.DepositRequest;
import com.tgilonis.cqrsbank.command.dto.WithdrawalRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bank-account")
public class BankAccountController
{
    @PostMapping(value = "/create")
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountRequest request)
    {
        return null;
    }

    @PutMapping(value = "/deposit")
    public ResponseEntity<String> deposit(@RequestBody DepositRequest request)
    {
        return null;
    }

    @PutMapping(value = "/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawalRequest request)
    {
        return null;
    }
}
