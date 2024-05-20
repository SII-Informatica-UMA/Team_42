package es.uma.informatica.sii.spring.jpa.demo.entities;

import java.util.Objects;
import java.util.List;
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
    @ElementCollection
    private List<String> alimentos; 
    private String recomendaciones;
    private String observaciones;
    private int idEntrenador;
    @ElementCollection
    private List<Integer> clientes;

    // GETTERS AND SETTERS
    public int getId() {
		return this.id;
	}
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
    public void setDescripcion(String desc) {
        this.descripcion = desc;
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

    public List<String> getAlimentos() {
        return alimentos;
    }
    public void setAlimentos(List<String> alm) {
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

    public List<Integer> getClientes() {
        return clientes;
    }
    public void setClientes(List<Integer> cli) {
        this.clientes = cli;
    }

    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String ob) {
        this.observaciones = ob;
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