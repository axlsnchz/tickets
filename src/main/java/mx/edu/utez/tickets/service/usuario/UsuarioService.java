package mx.edu.utez.tickets.service.usuario;

import mx.edu.utez.tickets.controller.usuario.dto.RequestCrearUsuarioDto;
import mx.edu.utez.tickets.exception.BadRequestException;
import mx.edu.utez.tickets.model.usuario.Usuario;
import mx.edu.utez.tickets.repository.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario createUser (RequestCrearUsuarioDto payload) {
        if (usuarioRepository.findByUsername(payload.getNombre()).isPresent()){
            throw new BadRequestException("Ese Usuario ya existe");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setNombre(payload.getNombre());
        newUsuario.setActivo(true);

        return usuarioRepository.save(newUsuario);
    }

    public Usuario desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("El usuario no existe"));

        usuario.setActivo(false);
        return usuarioRepository.save(usuario);
    }
}