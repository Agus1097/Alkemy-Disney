package ar.com.alkemy.alkemy.entities;

import javax.persistence.*;

@Entity
@Table(name = "personaje")
public class Personaje {

    @Column(name = "personaje_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personajeId;

    private String imagen;

    private String nombre;

    private Integer edad;

    private double peso;

    private String historia;

    @ManyToOne
    @JoinColumn(name = "pelicula_id", referencedColumnName = "pelicula_id")
    private Pelicula pelicula;

    public Integer getPersonajeId() {
        return personajeId;
    }

    public void setPersonajeId(Integer personajeId) {
        this.personajeId = personajeId;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
        this.pelicula.agregarPersonaje(this);
    }

}
