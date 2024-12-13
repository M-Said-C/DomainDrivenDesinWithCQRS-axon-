package com.example.tickets_microservice.sagga.commands.validations;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ValidateEventCommand {
    @TargetAggregateIdentifier
    private final Long evenementId;
    private final String codeTicket;

    public ValidateEventCommand(Long evenementId, String codeTicket) {
        this.evenementId = evenementId;
        this.codeTicket = codeTicket;
    }

    public Long getEvenementId() {
        return evenementId;
    }

    public String getCodeTicket() {
        return codeTicket;
    }
}
