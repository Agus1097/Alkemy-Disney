package ar.com.alkemy.alkemy.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ar.com.alkemy.alkemy.entities.Genero;
import ar.com.alkemy.alkemy.models.response.GenericResponse;
import ar.com.alkemy.alkemy.services.GeneroService;
import ar.com.alkemy.alkemy.services.UsuarioService;

@RestController
public class GeneroController {

    @Autowired
    GeneroService generoService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/genres/create")
    public ResponseEntity<GenericResponse> crearGenero(@RequestBody Genero genero) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        generoService.crearGenero(genero);
        GenericResponse respuesta = new GenericResponse();
        respuesta.isOk = true;
        respuesta.id = genero.getGeneroId();
        respuesta.mensaje = "El género ha sido creado con éxito.";

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/genres")
    public ResponseEntity<List<Genero>> mostrarGeneros() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        return ResponseEntity.ok(generoService.mostrarTodosGeneros());
    }

    @GetMapping("/genres/{nombre}")
    public ResponseEntity<Genero> buscarGeneroPorNombre(@PathVariable String nombre) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        return ResponseEntity.ok(generoService.buscarGeneroPorNombre(nombre));
    }
}
