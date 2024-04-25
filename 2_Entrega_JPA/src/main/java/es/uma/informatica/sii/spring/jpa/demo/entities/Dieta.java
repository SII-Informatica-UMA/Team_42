package es.uma.informatica.sii.spring.jpa.demo.entities;

import java.util.Objects;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Dieta {
    @Id @GeneratedValue
    private int id; 
    private String nombre;
    private String descripcion;
    private String objetivo;
    private int duracionDias; 
    private String[] alimentos; 
    private String recomendaciones;
    private int idEntrenador;
    @ElementCollection
    private int [] clientes;

    // GETTERS AND SETTERS
    public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getObjetivo() {
        return objetivo;
    }
    public void setObjetivo(String obj){
        this.objetivo = obj;
    }
    public int getDuracionDias() {
        return duracionDias;
    }
    public void setDuracionDias(int d) {
        this.duracionDias = d;
    }
    public String[] getAlimentos() {
        return alimentos;
    }
    public void setAlimentos(String[] alm) {
        this.alimentos = alm;
    }
    public String getRecomendaciones() {
        return recomendaciones;
    }
    public void setRecomendaciones(String recm) {
        this.recomendaciones = recm;
    }
    public int getIDEntrenador() {
        return idEntrenador;
    }
    public void setIDEntrenador(int id) {
        this.idEntrenador = id;
    }
    public int[] getClientes() {
        return clientes;
    }
    public void setClientes(int[] cli) {
        this.clientes = cli;
    }


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dieta other = (Dieta) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public String toString() {
		return "Dieta [id=" + id + ", nombre=" + nombre + ", objetivo=" + objetivo + "]";
	}
}
