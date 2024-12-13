package com.example.tickets_microservice.commands;


import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class UpdateTicketCommand {
    @TargetAggregateIdentifier
    private final String codeTicket;
    private final Double prixTicket;
    private final String typeTicket;

    public UpdateTicketCommand(String codeTicket, Double prixTicket, String typeTicket) {
        this.codeTicket = codeTicket;
        this.prixTicket = prixTicket;
        this.typeTicket = typeTicket;
    }

    public String getCodeTicket() {
        return codeTicket;
    }

    public Double getPrixTicket() {
        return prixTicket;
    }

    public String getTypeTicket() {
        return typeTicket;
    }
}
