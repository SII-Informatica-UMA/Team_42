package es.uma.informatica.sii.spring.jpa.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import es.uma.informatica.sii.spring.jpa.demo.entities.Dieta;

@Repository
public interface DietaRepository extends JpaRepository<Dieta, Long> {
	Optional<Dieta> findByNombre(String nombre);
	Optional<Dieta> findById(int id);
	Optional<Dieta> findByIdEntrenador(int id);
	

	@Query("SELECT d FROM Dieta d WHERE :idCliente MEMBER OF d.clientes")
	Optional<Dieta> findDietaByClienteId(@Param("idCliente") int idCliente);
}
