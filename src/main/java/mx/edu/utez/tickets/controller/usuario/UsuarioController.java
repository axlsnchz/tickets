package mx.edu.utez.tickets.controller.usuario;

import jakarta.validation.Valid;
import mx.edu.utez.tickets.controller.usuario.dto.RequestCrearUsuarioDto;
import mx.edu.utez.tickets.model.usuario.Usuario;
import mx.edu.utez.tickets.service.usuario.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody @Valid RequestCrearUsuarioDto payload) {
        return ResponseEntity.status(201).body(this.usuarioService.createUser(payload));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivarUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(this.usuarioService.desactivarUsuario(id));
    }
}