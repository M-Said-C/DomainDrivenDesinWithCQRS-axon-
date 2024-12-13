package com.example.tickets_microservice.sagga.commands.validations;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ValidateInternauteCommand {
    @TargetAggregateIdentifier
    private final Long internauteId;
    private final String codeTicket;

    public ValidateInternauteCommand(Long internauteId, String codeTicket) {
        this.internauteId = internauteId;
        this.codeTicket = codeTicket;
    }

    public Long getInternauteId() {
        return internauteId;
    }

    public String getCodeTicket() {
        return codeTicket;
    }
}
