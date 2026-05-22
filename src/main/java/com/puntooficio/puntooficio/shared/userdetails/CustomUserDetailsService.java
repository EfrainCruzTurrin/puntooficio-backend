package com.puntooficio.puntooficio.shared.userdetails;

import com.puntooficio.puntooficio.cliente.models.Cliente;
import com.puntooficio.puntooficio.cliente.repositories.ClienteRepository;
import com.puntooficio.puntooficio.shared.enums.Role;
import com.puntooficio.puntooficio.trabajador.models.Trabajador;
import com.puntooficio.puntooficio.trabajador.repositories.TrabajadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.puntooficio.puntooficio.shared.enums.Role;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ClienteRepository clienteRepository;
    private final TrabajadorRepository trabajadorRepository;

    // Spring Security llama a este método con el "identifier" del login.
    // Estrategia: primero buscamos en Cliente por email,
    // si no encontramos buscamos en Trabajador por telefono.
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // Cliente: busca por email
        Optional<Cliente> cliente = clienteRepository.findByEmail(identifier);
        if (cliente.isPresent()) {
            return new CustomUserDetails(
                    cliente.get().getId(),
                    identifier,
                    cliente.get().getPassword(),
                    Role.CLIENTE
            );
        }

        // Trabajador: busca por teléfono, luego por email
        Optional<Trabajador> trabajador = trabajadorRepository.findByTelefono(identifier);
        if (!trabajador.isPresent()) {
            trabajador = trabajadorRepository.findByEmail(identifier);
        }
        if (trabajador.isPresent()) {
            return new CustomUserDetails(
                    trabajador.get().getId(),
                    identifier,
                    trabajador.get().getPassword(),
                    Role.TRABAJADOR
            );
        }

        // Admin hardcodeado para desarrollo
        if (identifier.equals("admin@puntooficio.com")) {
            String adminPassword = "$2a$10$BU9GTmDcWrqcUeb7oyVyEuoqnnmScCaVzb3SwGjLaJhSIkE6qXclW"; // "admin1234"
            return new CustomUserDetails(0L, identifier, adminPassword, Role.ADMIN);
        }

        throw new UsernameNotFoundException("Usuario no encontrado: " + identifier);
    }
}