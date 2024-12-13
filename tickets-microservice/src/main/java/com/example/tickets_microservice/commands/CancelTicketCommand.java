package com.example.tickets_microservice.commands;


import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CancelTicketCommand {
    @TargetAggregateIdentifier
    private final String codeTicket;

    public CancelTicketCommand(String codeTicket) {
        this.codeTicket = codeTicket;
    }

    public String getCodeTicket() {
        return codeTicket;
    }
}
