package ar.com.alkemy.alkemy.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.alkemy.alkemy.entities.Pelicula;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula,Integer> {

    Pelicula findByPeliculaId(Integer id);

    Pelicula findByTitulo(String nombre);
    
}
