package es.uma.informatica.sii.spring.jpa.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uma.informatica.sii.spring.jpa.demo.entities.Dieta;

public interface DietaRepository extends JpaRepository<Dieta, Integer> {
    // Spring Data JPA proporciona métodos CRUD básicos de forma automática
}
