package com.example.tickets_microservice.controllers;


import com.example.tickets_microservice.command.CreateTicketCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketCommandController {

    private final CommandGateway commandGateway;

    public TicketCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createTicket(@RequestBody CreateTicketCommand command) {
        commandGateway.sendAndWait(command);
        return "Ticket created successfully!";
    }
}
