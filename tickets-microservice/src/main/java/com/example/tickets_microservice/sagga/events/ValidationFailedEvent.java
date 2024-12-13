package com.example.tickets_microservice.sagga.events;

public class ValidationFailedEvent {
    private final String codeTicket;
    private final String reason;

    public ValidationFailedEvent(String codeTicket, String reason) {
        this.codeTicket = codeTicket;
        this.reason = reason;
    }

    public String getCodeTicket() {
        return codeTicket;
    }

    public String getReason() {
        return reason;
    }
}
