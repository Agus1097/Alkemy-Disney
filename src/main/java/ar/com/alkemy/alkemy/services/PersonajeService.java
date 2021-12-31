package ar.com.alkemy.alkemy.services;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.alkemy.alkemy.entities.Pelicula;
import ar.com.alkemy.alkemy.entities.Personaje;
import ar.com.alkemy.alkemy.models.response.PersonajeResponse;
import ar.com.alkemy.alkemy.repos.PersonajeRepository;

@Service
public class PersonajeService {

    @Autowired
    PersonajeRepository personajeRepository;

    @Autowired
    PeliculaService peliculaService;

    public Personaje buscarPersonajePorId(Integer id) {
        return personajeRepository.findByPersonajeId(id);
    }

    public Personaje crearPersonaje(Integer edad, String historia, String imagen, String nombre, double peso,
            Integer peliculaId) {
        Personaje personaje = new Personaje();
        Pelicula pelicula = peliculaService.buscarPeliculaPorId(peliculaId);
        personaje.setEdad(edad);
        personaje.setHistoria(historia);
        personaje.setImagen(imagen);
        personaje.setNombre(nombre);
        personaje.setPeso(peso);
        personaje.setPelicula(pelicula);
        pelicula.agregarPersonaje(personaje);

        return personajeRepository.save(personaje);
    }

    public Personaje modificarPersonaje(Integer id, Integer peliculaId, Integer edadNueva, String historiaNueva,
            String imagenNueva, double pesoNuevo) {
        Personaje personaje = buscarPersonajePorId(id);
        Pelicula pelicula = peliculaService.buscarPeliculaPorId(peliculaId);
        personaje.setEdad(edadNueva);
        personaje.setHistoria(historiaNueva);
        personaje.setImagen(imagenNueva);
        personaje.setPelicula(pelicula);
        personaje.setPeso(pesoNuevo);
        return personajeRepository.save(personaje);
    }

    public void eliminarPersonaje(Integer id) {
        personajeRepository.deleteById(id);
    }

    public List<Personaje> mostrarTodosPersonajes() {
        return personajeRepository.findAll();
    }

    public List<PersonajeResponse> mostrarPersonajes(String imagen, String nombre) {
        List<PersonajeResponse> listaPersonajes = new ArrayList<>();

        for (Personaje personaje : this.mostrarTodosPersonajes()) {
            PersonajeResponse listaPersonajesImagenNombre = new PersonajeResponse();
            listaPersonajesImagenNombre.imagen = personaje.getImagen();
            listaPersonajesImagenNombre.nombre = personaje.getNombre();

            listaPersonajes.add(listaPersonajesImagenNombre);
        }
        return listaPersonajes;
    }

    public Personaje buscarPersonajePorNombre(String nombre) {
        return personajeRepository.findByNombre(nombre);
    }

    public List<Personaje> buscarPersonajesPorPelicula(Integer idMovie) {
        Pelicula pelicula = peliculaService.buscarPeliculaPorId(idMovie);
        return pelicula.getPersonajes();
    }

    public Personaje buscarPersonajePorEdad(Integer edad) {
        return personajeRepository.findByEdad(edad);
    }

}
