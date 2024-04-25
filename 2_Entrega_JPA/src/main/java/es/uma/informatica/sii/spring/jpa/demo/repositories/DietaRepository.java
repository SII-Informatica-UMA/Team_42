package es.uma.informatica.sii.spring.jpa.demo.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uma.informatica.sii.spring.jpa.demo.entities.Dieta;

public interface DietaRepository extends JpaRepository<Dieta, Integer> {
    List<Dieta> findByNombre(String nombre);
    //List<Dieta> findByNombre(String title);
	List<Dieta> findById(int id, String nombre);
    List<Dieta> findByEntrenadorId(int id);


    @Query("SELECT d FROM Dieta d WHERE :clienteId IN d.clientes")
    Set<Dieta> findByClienteId(@Param("clienteId") int clienteId);
	
	@Query("select b from Dieta b where b.nombre = :nombre")
	List<Dieta> miConsultaCompleja(@Param("nombre") String nombre);
}
