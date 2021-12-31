package ar.com.alkemy.alkemy.controllers;

import ar.com.alkemy.alkemy.services.UsuarioService;
import ar.com.alkemy.alkemy.services.GeneroService;
import ar.com.alkemy.alkemy.services.PeliculaService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ar.com.alkemy.alkemy.entities.Pelicula;
import ar.com.alkemy.alkemy.models.request.PeliculaRequest;
import ar.com.alkemy.alkemy.models.response.GenericResponse;
import ar.com.alkemy.alkemy.models.response.PeliculaResponse;

@RestController
public class PeliculaController {

    @Autowired
    PeliculaService peliculaService;

    @Autowired
    GeneroService generoService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/movies")
    public ResponseEntity<List<PeliculaResponse>> mostrarPeliculas() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        return ResponseEntity.ok(peliculaService.mostrarPeliculas());
    }

    @PostMapping("/movies/create")
    public ResponseEntity<GenericResponse> crearPelicula(@RequestBody PeliculaRequest peliculaRequest, Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        Pelicula pelicula = peliculaService.crearPelicula(peliculaRequest.generoId, peliculaRequest.calificacion,
                peliculaRequest.fechaCreacion, peliculaRequest.imagen, peliculaRequest.titulo);

        GenericResponse respuesta = new GenericResponse();
        respuesta.isOk = true;
        respuesta.id = pelicula.getPeliculaId();
        respuesta.mensaje = "La película ha sido creada con exito.";

        return ResponseEntity.ok(respuesta);

    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<?> mostrarPeliculaPorId(@PathVariable Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        if (peliculaService.buscarPeliculaPorId(id) == null) {
            GenericResponse respuesta = new GenericResponse();
            respuesta.isOk = false;
            respuesta.mensaje = "El id ingresado no existe";
            return ResponseEntity.badRequest().body(respuesta);
        } else {
            return ResponseEntity.ok(peliculaService.buscarPeliculaPorId(id));
        }
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<GenericResponse> modificarPelicula(@PathVariable Integer id,
            @RequestBody PeliculaRequest peliculaRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        Pelicula pelicula = peliculaService.modificarPelicula(id, peliculaRequest.calificacion,
                peliculaRequest.generoId, peliculaRequest.fechaCreacion, peliculaRequest.imagen,
                peliculaRequest.titulo);

        GenericResponse respuesta = new GenericResponse();
        respuesta.isOk = true;
        respuesta.id = pelicula.getPeliculaId();
        respuesta.mensaje = "La película ha sido modificada.";

        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("movies/{id}")
    public ResponseEntity<GenericResponse> eliminarPelicula(@PathVariable Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        GenericResponse respuesta = new GenericResponse();

        if (peliculaService.buscarPeliculaPorId(id) == null) {
            respuesta.isOk = false;
            respuesta.mensaje = "El id ingresado no existe";
            return ResponseEntity.badRequest().body(respuesta);
        } else {
            peliculaService.eliminarPelicula(id);
            respuesta.isOk = true;
            respuesta.mensaje = "La película ha sido eliminada.";
            return ResponseEntity.ok(respuesta);
        }
    }

    @GetMapping("movies/name/{nombre}")
    public ResponseEntity<Pelicula> buscarPeliculaPorNombre(@PathVariable String nombre) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        return ResponseEntity.ok(peliculaService.buscarPeliculaPorNombre(nombre));
    }

    @GetMapping("/movies/genre/{idGenero}")
    public ResponseEntity<List<Pelicula>> buscarPeliculaPorIdGenero(@PathVariable Integer idGenero) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        usuarioService.buscarPorUsername(username);

        return ResponseEntity.ok(peliculaService.buscarPeliculasPorGenero(idGenero));
    }

    @GetMapping("/movies/order/ASC")
    public ResponseEntity<List<Pelicula>> ordenarPeliculaFechaASC() {
        return ResponseEntity.ok(peliculaService.ordenarPeliculasASC());
    }

    @GetMapping("/movies/order/DESC")
    public ResponseEntity<List<Pelicula>> ordenarPeliculaFechaDESC() {
        return ResponseEntity.ok(peliculaService.ordenarPeliculasDESC());
    }

}
