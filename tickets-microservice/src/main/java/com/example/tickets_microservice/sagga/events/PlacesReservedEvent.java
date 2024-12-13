package com.example.tickets_microservice.sagga.events;


public class PlacesReservedEvent {

    private final Long evenementId;
    private final int reservedPlaces;
    private final String codeTicket;
    private final String typeTicket;
    private final Double prixTicket; // Add this field

    public PlacesReservedEvent(Long evenementId, int reservedPlaces, String codeTicket, String typeTicket, Double prixTicket) {
        this.evenementId = evenementId;
        this.reservedPlaces = reservedPlaces;
        this.codeTicket = codeTicket;
        this.typeTicket = typeTicket;
        this.prixTicket = prixTicket;
    }

    // Getters
    public Long getEvenementId() {
        return evenementId;
    }

    public int getReservedPlaces() {
        return reservedPlaces;
    }

    public String getCodeTicket() {
        return codeTicket;
    }

    public String getTypeTicket() {
        return typeTicket;
    }

    public Double getPrixTicket() { // Add this getter
        return prixTicket;
    }
}