package ar.com.alkemy.alkemy.services;

import ar.com.alkemy.alkemy.security.jwt.JWTTokenUtil;
import ar.com.alkemy.alkemy.entities.Usuario;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario u = usuarioService.buscarPorUsername(username);

        if (u != null) {
            return new User(u.getUsername(), u.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    public UserDetails loadUserByUsername(String username, String jwtToken) throws UsernameNotFoundException {

        Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        claims.forEach((key, value) -> {

            authorities.add(new SimpleGrantedAuthority("CLAIM_" + key + "_" + value));

        });

        return new User(username, "", authorities);

    }
}