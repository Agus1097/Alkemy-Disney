package ar.com.alkemy.alkemy.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.alkemy.alkemy.entities.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer>{

    Genero findByGeneroId(Integer id);

    Genero findByNombre(String nombre);
    
}
