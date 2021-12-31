package ar.com.alkemy.alkemy.controllers;

import ar.com.alkemy.alkemy.models.response.RegistrationResponse;
import ar.com.alkemy.alkemy.models.request.RegistrationRequest;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ar.com.alkemy.alkemy.models.request.LoginRequest;
import ar.com.alkemy.alkemy.models.response.LoginResponse;
import ar.com.alkemy.alkemy.entities.Usuario;
import ar.com.alkemy.alkemy.security.jwt.JWTTokenUtil;
import ar.com.alkemy.alkemy.services.JWTUserDetailsService;
import ar.com.alkemy.alkemy.services.UsuarioService;

@RestController
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @PostMapping("/auth/register")
    public ResponseEntity<RegistrationResponse> postRegisterUser(@RequestBody RegistrationRequest req,
            BindingResult results) {
        RegistrationResponse respuesta = new RegistrationResponse();

        Usuario usuario = usuarioService.crearUsuario(req.nombreCompleto, req.fechaDeNacimiento, req.identification, req.email, req.password);

        respuesta.isOk = true;
        respuesta.message = "Te registraste con exitoooo!!!!!!!";
        respuesta.userId = usuario.getUsuarioId();

        return ResponseEntity.ok(respuesta);

    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest,
            BindingResult results) throws Exception {

        Usuario usuarioLogueado = usuarioService.login(authenticationRequest.username, authenticationRequest.password);

        UserDetails userDetails = usuarioService.getUserAsUserDetail(usuarioLogueado);
        Map<String, Object> claims = usuarioService.getUserClaims(usuarioLogueado);

        String token = jwtTokenUtil.generateToken(userDetails, claims);

        Usuario u = usuarioService.buscarPorUsername(authenticationRequest.username);

        LoginResponse r = new LoginResponse();
        r.id = u.getUsuarioId();
        r.username = authenticationRequest.username;
        r.email = u.getEmail();
        r.token = token;

        return ResponseEntity.ok(r);

    }

}
