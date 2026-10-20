package mx.edu.utez.tickets.service.ticket;

import mx.edu.utez.tickets.controller.ticket.dto.RequestCambiarEstadoDto;
import mx.edu.utez.tickets.controller.ticket.dto.RequestCambiarPrioridadDto;
import mx.edu.utez.tickets.controller.ticket.dto.RequestCrearTicketDto;
import mx.edu.utez.tickets.exception.BadRequestException;
import mx.edu.utez.tickets.model.ticket.Estado;
import mx.edu.utez.tickets.model.ticket.Prioridad;
import mx.edu.utez.tickets.model.ticket.Ticket;
import mx.edu.utez.tickets.model.usuario.Usuario;
import mx.edu.utez.tickets.repository.ticket.TicketRepository;
import mx.edu.utez.tickets.repository.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TicketService {

    private final UsuarioRepository usuarioRepository;
    private final TicketRepository ticketRepository;

    public TicketService(UsuarioRepository usuarioRepository, TicketRepository ticketRepository) {
        this.usuarioRepository = usuarioRepository;
        this.ticketRepository = ticketRepository;
    }
    public Ticket createTicket(RequestCrearTicketDto payload) {
        Usuario usuario = usuarioRepository.findById(payload.getUsuarioId())
                .orElseThrow(() -> new BadRequestException("El usuario no existe"));

        if (!usuario.isActivo()) {
            throw new BadRequestException("El usuario no esta activo");
        }

        Ticket newTicket = new Ticket();
        newTicket.setDescripcion(payload.getDescripcion());
        newTicket.setEstado(Estado.ABIERTO);
        newTicket.setPrioridad(Prioridad.valueOf(payload.getPrioridad()));
        newTicket.setUsuario(usuario);

        return ticketRepository.save(newTicket);
    }

    public Ticket cambiarEstado(RequestCambiarEstadoDto payload) {
        Ticket ticket = ticketRepository.findById(payload.getId())
                .orElseThrow(() -> new BadRequestException("El ticket no existe"));

        if (ticket.getEstado() == Estado.CERRADO) {
            throw new BadRequestException("El ticket ya está CERRADO, no se puede modificar");
        }

        Estado nuevoEstado = Estado.valueOf(payload.getEstado());

        if (ticket.getEstado() == Estado.ABIERTO && nuevoEstado != Estado.EN_PROCESO) {
            throw new BadRequestException("El ticket ABIERTO solo puede pasar a EN_PROCESO");
        }
        if (ticket.getEstado() == Estado.EN_PROCESO && nuevoEstado != Estado.CERRADO) {
            throw new BadRequestException("El ticket EN_PROCESO solo puede pasar a CERRADO");
        }

        ticket.setEstado(nuevoEstado);
        return ticketRepository.save(ticket);
    }

    public Ticket cambiarPrioridad(RequestCambiarPrioridadDto payload) {
        Ticket ticket = ticketRepository.findById(payload.getId())
                .orElseThrow(() -> new BadRequestException("El ticket no existe"));

        if (ticket.getEstado() == Estado.CERRADO) {
            throw new BadRequestException("No se puede cambiar la prioridad de un ticket CERRADO");
        }

        ticket.setPrioridad(Prioridad.valueOf(payload.getPrioridad()));
        return ticketRepository.save(ticket);
    }

    public List<Ticket> obtenerTicketsUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new BadRequestException("El usuario no existe");
        }
        return ticketRepository.findByUsuarioId(usuarioId);
    }
}