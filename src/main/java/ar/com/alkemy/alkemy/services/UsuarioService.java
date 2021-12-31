package ar.com.alkemy.alkemy.services;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import ar.com.alkemy.alkemy.entities.Usuario;
import ar.com.alkemy.alkemy.repos.UsuarioRepository;
import ar.com.alkemy.alkemy.security.Crypto;
import ar.com.alkemy.alkemy.sistema.comm.EmailService;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EmailService emailService;

    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario login(String username, String password) {

        Usuario u = buscarPorUsername(username);

        if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getEmail().toLowerCase()))) {

            throw new BadCredentialsException("Usuario o contraseña invalida");
        }

        return u;
    }

    public Usuario crearUsuario(String nombre, Date fechaNacimiento, String documento, String email, String password) {

        Usuario usuario = new Usuario();
        usuario.setUsername(email);
        usuario.setEmail(email);
        usuario.setPassword(Crypto.encrypt(password, email.toLowerCase()));
        usuarioRepository.save(usuario);

        emailService.SendEmail(usuario.getEmail(), "Registrado correctamente", "¡Bienvenido a Disney Alkemy! Te registraste con éxito");
        return usuario;
    }

    public Usuario buscarPorId(Integer id) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);

        if (usuarioOp.isPresent()) {
            return usuarioOp.get();
        }
        return null;
    }

    public Map<String, Object> getUserClaims(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("userType", usuario.getUsuarioId()); ///

        return claims;
    }

    public UserDetails getUserAsUserDetail(Usuario usuario) {
        UserDetails uDetails;

        uDetails = new User(usuario.getUsername(), usuario.getPassword(), getAuthorities(usuario));

        return uDetails;
    }

    Set<? extends GrantedAuthority> getAuthorities(Usuario usuario) {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        Integer userType = usuario.getUsuarioId();

        authorities.add(new SimpleGrantedAuthority("CLAIM_userType_" + userType.toString()));

        return authorities;
    }

}
