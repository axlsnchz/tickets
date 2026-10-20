package mx.edu.utez.tickets.controller.ticket;

import jakarta.validation.Valid;
import mx.edu.utez.tickets.controller.ticket.dto.RequestCambiarEstadoDto;
import mx.edu.utez.tickets.controller.ticket.dto.RequestCambiarPrioridadDto;
import mx.edu.utez.tickets.controller.ticket.dto.RequestCrearTicketDto;
import mx.edu.utez.tickets.model.ticket.Ticket;
import mx.edu.utez.tickets.service.ticket.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/ticket")
@CrossOrigin("*")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody @Valid RequestCrearTicketDto payload) {
        return ResponseEntity.status(201).body(this.ticketService.createTicket(payload));
    }

    @PutMapping("/estado")
    public ResponseEntity<Ticket> cambiarEstado(@RequestBody @Valid RequestCambiarEstadoDto payload){
        return ResponseEntity.ok(this.ticketService.cambiarEstado(payload));
    }

    @PutMapping("/prioridad")
    public ResponseEntity<Ticket> cambiarPrioridad(@RequestBody @Valid RequestCambiarPrioridadDto payload){
        return ResponseEntity.ok(this.ticketService.cambiarPrioridad(payload));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Ticket>> obtenerPorUsuario(@PathVariable Long usuarioId){
        return ResponseEntity.ok(this.ticketService.obtenerTicketsUsuario(usuarioId));
    }
}