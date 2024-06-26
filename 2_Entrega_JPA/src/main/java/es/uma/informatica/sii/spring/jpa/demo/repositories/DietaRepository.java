package es.uma.informatica.sii.spring.jpa.demo.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uma.informatica.sii.spring.jpa.demo.entities.Dieta;


public interface DietaRepository extends JpaRepository<Dieta, Integer> {
    List<Dieta> findByNombre(String nombre);
    List<Dieta> findByIdEntrenador(int id);
}