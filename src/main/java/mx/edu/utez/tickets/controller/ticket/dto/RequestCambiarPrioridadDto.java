package mx.edu.utez.tickets.controller.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCambiarPrioridadDto {

    @NotNull(message = "El ID es obligatorio")
    private Long id;

    @Pattern(regexp = "^(BAJA|MEDIA|ALTA)$", message = "La prioridad debe ser BAJA, MEDIA o ALTA")
    @NotBlank(message = "La prioridad es obligatoria")
    private String prioridad;
}