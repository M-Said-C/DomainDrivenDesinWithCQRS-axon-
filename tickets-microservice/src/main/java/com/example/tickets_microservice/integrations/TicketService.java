package com.example.tickets_microservice.integrations;

/*import com.example.tickets_microservice.aggregate.TicketAggregate;
import com.example.tickets_microservice.dtos.TicketDTO;
import com.example.tickets_microservice.repos.TicketRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EventService eventService;
    private final InternauteService internauteService;

    public TicketService(TicketRepository ticketRepository, EventService eventService, InternauteService internauteService) {
        this.ticketRepository = ticketRepository;
        this.eventService = eventService;
        this.internauteService = internauteService;
    }
    @CircuitBreaker(name = "addTicket", fallbackMethod = "ajouterTicketsEtAffecterAEvenementEtInternaute")
    public List<TicketAggregate> ajouterTicketsEtAffecterAEvenementEtInternaute(List<TicketAggregate> ticketAggregates, Long idEvenement, Long idInternaute) {
        // Validate Evenement
        @SuppressWarnings("unchecked")
        Map<String, Object> evenementDetails = (Map<String, Object>) eventService.getEventDetails(idEvenement);
        if (evenementDetails == null || !evenementDetails.containsKey("nbPlacesRestantes")) {
            throw new IllegalArgumentException("Invalid event ID or missing data");
        }

        int nbPlacesRestantes = (int) evenementDetails.get("nbPlacesRestantes");

        // Validate Internaute
        @SuppressWarnings("unchecked")
        Map<String, Object> internauteDetails = (Map<String, Object>) internauteService.getInternauteDetails(idInternaute);
        if (internauteDetails == null) {
            throw new IllegalArgumentException("Invalid internaute ID");
        }

        // Check if there are enough places available
        if (ticketAggregates.size() > nbPlacesRestantes) {
            throw new UnsupportedOperationException("nombre de places demandées indisponible");
        }

        // Update the number of available places in the event
        try {
            System.out.println("Calling eventService.updateNbPlace...");
            Object updateResponse = eventService.updateNbPlace(idEvenement, -ticketAggregates.size());
            System.out.println("Response from eventService.updateNbPlace: " + updateResponse);
            System.out.println("Successfully updated nbPlacesRestantes.");

        // Save tickets
        ticketAggregates.forEach(ticket -> {
            ticket.setEvenementId(idEvenement);
            ticket.setInternauteId(idInternaute);
        });

        return ticketRepository.saveAll(ticketAggregates);
        } catch (Exception e) {
            System.err.println("Exception occurred while updating nbPlacesRestantes: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw to identify the issue
        }
    }

    public TicketDTO addTicket(TicketDTO ticketDTO) {
        // Validate Evenement ID
        if (eventService.getEventDetails(ticketDTO.getEvenementId()) == null) {
            throw new IllegalArgumentException("Invalid event ID");
        }

        // Validate Internaute ID
        if (internauteService.getInternauteDetails(ticketDTO.getInternauteId()) == null) {
            throw new IllegalArgumentException("Invalid internaute ID");
        }


        // Save ticket
        TicketAggregate ticketAggregate = new TicketAggregate();
        ticketAggregate.setCodeTicket(ticketDTO.getCodeTicket());
        ticketAggregate.setPrixTicket(ticketDTO.getPrixTicket());
        ticketAggregate.setTypeTicket(ticketDTO.getTypeTicket());
        ticketAggregate.setEvenementId(ticketDTO.getEvenementId());
        ticketAggregate.setInternauteId(ticketDTO.getInternauteId());

        TicketAggregate savedTicketAggregate = ticketRepository.save(ticketAggregate);
        ticketDTO.setCodeTicket(savedTicketAggregate.getCodeTicket());
        return ticketDTO;
    }
    public List<TicketDTO> getAllTickets() {
        List<TicketAggregate> ticketAggregates = ticketRepository.findAll();
        return ticketAggregates.stream()
                .map(ticket -> {
                    TicketDTO ticketDTO = new TicketDTO();
                    ticketDTO.setCodeTicket(ticket.getCodeTicket());
                    ticketDTO.setPrixTicket(ticket.getPrixTicket());
                    ticketDTO.setTypeTicket(ticket.getTypeTicket());
                    ticketDTO.setEvenementId(ticket.getEvenementId());
                    ticketDTO.setInternauteId(ticket.getInternauteId());
                    return ticketDTO;
                })
                .collect(Collectors.toList());
    }

    public Double montantRecupereParEvtEtTypeTicket(String nomEvt, TicketAggregate.TypeTicket typeTicket) {
        try {
            // Fetch event details
            @SuppressWarnings("unchecked")
            Map<String, Object> evenementDetails = (Map<String, Object>) eventService.findByNomEvenement(nomEvt);
            // Validate event details
            if (evenementDetails == null || !evenementDetails.containsKey("id")) {
                throw new IllegalArgumentException("Invalid event name or missing data");
            }

            // Extract event ID
            Long evenementId = ((Number) evenementDetails.get("id")).longValue();
            System.out.println(evenementId);

            // Fetch tickets for the event and ticket type
            List<TicketAggregate> ticketAggregates = ticketRepository.findByEvenementIdAndTypeTicket(evenementId, typeTicket);

            // If no tickets are found, return 0
            if (ticketAggregates.isEmpty()) {
                return 0.0;
            }

            // Calculate the total amount
            double totalAmount = ticketAggregates.stream()
                    .mapToDouble(TicketAggregate::getPrixTicket)
                    .sum();

            return totalAmount;

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            throw e; // Propagate the exception for further handling if needed
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred while calculating the amount", e);
        }
    }
    public String internauteLePlusActif() {
        // Fetch all tickets
        List<TicketAggregate> ticketAggregates = ticketRepository.findAll();

        // Validate if there are any tickets
        if (ticketAggregates.isEmpty()) {
            throw new IllegalStateException("No tickets found");
        }

        // Group tickets by internauteId and count them
        Map<Long, Long> internauteTicketCount = ticketAggregates.stream()
                .collect(Collectors.groupingBy(TicketAggregate::getInternauteId, Collectors.counting()));

        // Find the internauteId with the maximum count
        Map.Entry<Long, Long> mostActiveInternaute = internauteTicketCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new IllegalStateException("No active internaute found"));

        // Return the internauteId as a String
        return String.valueOf(mostActiveInternaute.getKey());
    }
    public List<TicketAggregate> ajouterTicketsEtAffecterAEvenementEtInternaute(List<TicketAggregate> ticketAggregates, Long idEvenement, Long idInternaute, Throwable throwable) {
        System.err.println("Fallback: Failed to add tickets. Error: " + throwable.getMessage());
        return List.of(); // Return an empty list or any appropriate default value
    }

}
*/