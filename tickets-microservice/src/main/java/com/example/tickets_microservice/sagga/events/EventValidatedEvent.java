package com.example.tickets_microservice.sagga.events;

public class EventValidatedEvent {
    private final Long evenementId;
    private final String codeTicket;

    public EventValidatedEvent(Long evenementId, String codeTicket) {
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
