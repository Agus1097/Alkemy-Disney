package ar.com.alkemy.alkemy.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ar.com.alkemy.alkemy.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Usuario findByUsername(String userName);

    public Usuario findByEmail(String email);

}
