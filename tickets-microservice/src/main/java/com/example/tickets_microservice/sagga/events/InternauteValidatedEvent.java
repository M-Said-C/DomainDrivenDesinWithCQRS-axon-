package com.example.tickets_microservice.sagga.events;

public class InternauteValidatedEvent {
    private final Long internauteId;
    private final String codeTicket;

    public InternauteValidatedEvent(Long internauteId, String codeTicket) {
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
