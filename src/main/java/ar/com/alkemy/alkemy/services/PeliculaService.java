package ar.com.alkemy.alkemy.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.alkemy.alkemy.entities.Genero;
import ar.com.alkemy.alkemy.entities.Pelicula;
import ar.com.alkemy.alkemy.models.response.PeliculaResponse;
import ar.com.alkemy.alkemy.repos.PeliculaRepository;
import java.util.stream.Collectors;

@Service
public class PeliculaService {

    @Autowired
    PeliculaRepository peliculaRepository;

    @Autowired
    GeneroService generoService;

    public Pelicula buscarPeliculaPorId(Integer id) {
        return peliculaRepository.findByPeliculaId(id);
    }

    public Pelicula crearPelicula(Integer generoId, Integer calificacion, Date fechaCreacion,
            String imagen, String titulo) {
        Pelicula pelicula = new Pelicula();
        Genero genero = generoService.buscarPorGeneroId(generoId);
        pelicula.setCalificacion(calificacion);
        pelicula.setFechaCreacion(fechaCreacion);
        pelicula.setImagen(imagen);
        pelicula.setTitulo(titulo);
        pelicula.setGenero(genero);
        genero.agregarPelicula(pelicula);
        return peliculaRepository.save(pelicula);

    }

    public Pelicula modificarPelicula(Integer id, Integer generoId, Integer calificacion, Date fechaCreacion,
            String imagen, String titulo) {
        Pelicula pelicula = buscarPeliculaPorId(id);
        Genero genero = generoService.buscarPorGeneroId(generoId);
        pelicula.setCalificacion(calificacion);
        pelicula.setFechaCreacion(fechaCreacion);
        pelicula.setImagen(imagen);
        pelicula.setTitulo(titulo);
        genero.agregarPelicula(pelicula);

        return peliculaRepository.save(pelicula);

    }

    public void eliminarPelicula(Integer id) {
        peliculaRepository.deleteById(id);
    }

    public List<PeliculaResponse> mostrarPeliculas() {
        List<PeliculaResponse> peliculas = new ArrayList<>();

        for (Pelicula pelicula : this.mostrarTodasPeliculas()) {
            PeliculaResponse listaPeliculas = new PeliculaResponse();
            listaPeliculas.imagen = pelicula.getImagen();
            listaPeliculas.titulo = pelicula.getTitulo();
            listaPeliculas.fechaCreacion = pelicula.getFechaCreacion();

            peliculas.add(listaPeliculas);
        }
        return peliculas;

    }

    private List<Pelicula> mostrarTodasPeliculas() {
        return peliculaRepository.findAll();
    }

    public Pelicula buscarPeliculaPorNombre(String nombre) {
        return peliculaRepository.findByTitulo(nombre);
    }

    public List<Pelicula> buscarPeliculasPorGenero(Integer idGenero) {
        Genero genero = generoService.buscarPorGeneroId(idGenero);
        return genero.getPeliculas();
    }

    public List<Pelicula> ordenarPeliculasDESC() {
        return this.mostrarTodasPeliculas().stream().sorted(Comparator.comparing(Pelicula::getFechaCreacion).reversed()).collect(Collectors.toList());
    }
    
    public List<Pelicula> ordenarPeliculasASC() {
        return this.mostrarTodasPeliculas().stream().sorted(Comparator.comparing(Pelicula::getFechaCreacion)).collect(Collectors.toList());
    }

}
