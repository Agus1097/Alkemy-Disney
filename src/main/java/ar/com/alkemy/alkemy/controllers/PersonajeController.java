package ar.com.alkemy.alkemy.controllers;

import ar.com.alkemy.alkemy.services.UsuarioService;
import ar.com.alkemy.alkemy.services.PersonajeService;
import ar.com.alkemy.alkemy.services.PeliculaService;
import ar.com.alkemy.alkemy.models.response.GenericResponse;
import ar.com.alkemy.alkemy.models.response.PersonajeResponse;
import ar.com.alkemy.alkemy.entities.Personaje;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ar.com.alkemy.alkemy.models.request.PersonajeRequest;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PersonajeController {

    @Autowired
    PersonajeService personajeService;

    @Autowired
    PeliculaService peliculaService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/characters")
    public ResponseEntity<List<PersonajeResponse>> mostrarPersonajes(String imagen, String nombre) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        List<PersonajeResponse> personajes = personajeService.mostrarPersonajes(imagen, nombre);

        return ResponseEntity.ok(personajes);
    }

    @PostMapping("/characters/create")
    public ResponseEntity<GenericResponse> crearPersonaje(@RequestBody PersonajeRequest personajeRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        GenericResponse respuesta = new GenericResponse();
        Personaje personaje = personajeService.crearPersonaje(personajeRequest.edad, personajeRequest.historia,
                personajeRequest.imagen, personajeRequest.nombre, personajeRequest.peso, personajeRequest.peliculaId);

        respuesta.id = personaje.getPersonajeId();
        respuesta.isOk = true;
        respuesta.mensaje = "El personaje ha sido creado con éxito.";

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/characters/{id}")
    public ResponseEntity<GenericResponse> modificarPersonaje(@PathVariable Integer id,
            @RequestBody PersonajeRequest personajeRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        GenericResponse respuesta = new GenericResponse();

        if (personajeService.buscarPersonajePorId(id) == null) {

            respuesta.isOk = false;
            respuesta.mensaje = "El id ingresado no existe";
            return ResponseEntity.badRequest().body(respuesta);

        } else {

            Personaje personaje = personajeService.modificarPersonaje(id, personajeRequest.peliculaId, personajeRequest.edad,
                    personajeRequest.historia, personajeRequest.imagen, personajeRequest.peso);
            respuesta.id = personaje.getPersonajeId();
            respuesta.isOk = true;
            respuesta.mensaje = "El personaje ha sido modificado";
            return ResponseEntity.ok(respuesta);

        }

    }

    @DeleteMapping("/characters/{id}")
    public ResponseEntity<GenericResponse> eliminarPersonaje(@PathVariable Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        GenericResponse respuesta = new GenericResponse();

        if (personajeService.buscarPersonajePorId(id) == null) {
            respuesta.isOk = false;
            respuesta.mensaje = "El id ingresado no existe";
            return ResponseEntity.badRequest().body(respuesta);

        } else {
            personajeService.eliminarPersonaje(id);
            respuesta.isOk = true;
            respuesta.mensaje = "El personaje ha sido eliminado.";
            return ResponseEntity.ok(respuesta);

        }
    }

    @GetMapping("/characters/{id}")
    public ResponseEntity<?> buscarPersonajePorId(@PathVariable Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        if (personajeService.buscarPersonajePorId(id) == null) {
            GenericResponse respuesta = new GenericResponse();
            respuesta.mensaje = "El id ingresado no existe";
            return ResponseEntity.badRequest().body(respuesta);
        } else {
            return ResponseEntity.ok(personajeService.buscarPersonajePorId(id));
        }
    }

    @GetMapping("/characters/name/{nombre}")
    public ResponseEntity<Personaje> buscarPersonajePorNombre(@PathVariable String nombre) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        return ResponseEntity.ok(personajeService.buscarPersonajePorNombre(nombre));
    }

    @GetMapping("/characters/age/{edad}")
    public ResponseEntity<Personaje> buscarPersonajePorEdad(@PathVariable Integer edad) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        return ResponseEntity.ok(personajeService.buscarPersonajePorEdad(edad));
    }

    @GetMapping("/characters/movies/{idMovie}")
    public ResponseEntity<List<Personaje>> buscarPersonajesPorIdPelicula(@PathVariable Integer idMovie) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        return ResponseEntity.ok(personajeService.buscarPersonajesPorPelicula(idMovie));
    }

}
