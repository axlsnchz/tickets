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
public class RequestCambiarEstadoDto {

    @NotNull(message = "El ID es obligatorio")
    private Long id;

    @Pattern(regexp = "^(ABIERTO|EN_PROCESO|CERRADO)$", message = "El estado debe ser ABIERTO, EN_PROCESO o CERRADO")
    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}