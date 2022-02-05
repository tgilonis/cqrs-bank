# Basic Bank Account Management System using Spring, Axon and CQRS

The user of this bank account management system is able to create a wallet, add funds, withdraw funds, transfer funds to
a different wallet, and delete a wallet.

The primary aim of this project is to use event sourcing for these actions. The secondary aim of this project will be to
explore how to implement automated tests for these actions, and thus how to combine the implementation of the Command 
Query Responsibility Segregation (CQRS) design pattern with TDD principles.

Tech Stack:
- JDK 11
- Spring Boot (2.6.2)
- Axon (4.5.8)
- Maven
- JPA
- JUnit (5.8.2)
- Mockito
- Hamcrest