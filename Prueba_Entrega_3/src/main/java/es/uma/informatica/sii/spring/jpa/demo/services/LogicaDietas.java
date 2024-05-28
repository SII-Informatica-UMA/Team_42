package es.uma.informatica.sii.spring.jpa.demo.services;

import es.uma.informatica.sii.spring.jpa.demo.entities.Dieta;
import es.uma.informatica.sii.spring.jpa.demo.repositories.DietaRepository;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LogicaDietas {
    private DietaRepository dietaRepo;
    

    public LogicaDietas(DietaRepository dietaRepo) {
        this.dietaRepo = dietaRepo;
    }

    // Devuelve todas las dietas de la base de datos
    public List<Dieta> getDietas() {
        return dietaRepo.findAll();
    }

    // Añade una dieta a la base de datos
    public Dieta addDieta(Dieta dieta, int idEntrenador){
        dieta.setId(null);
        dieta.setIDEntrenador(idEntrenador);
        dietaRepo.findByNombre(dieta.getNombre()).ifPresent(n -> {
            throw new IllegalArgumentException("La dieta ya existe");
        });
        return dietaRepo.save(dieta);
    }

    // Elimina la dieta con el id dado si existe y si el entrenador que la quiere borrar es el que la creo
    // Falta comprobar que el entrenador que la quiere borrar es el que la creo
    public void deleteDieta(Long id){
        var dieta = dietaRepo.findById(id);
        if(dieta.isPresent()){
            dietaRepo.deleteById(id);
        }else{
            throw new IllegalArgumentException("No existe la dieta con id " + id);
        }
    }

    // Devuelve la dieta con el id dado si existe
    public Dieta getDieta(Long id){
        var dieta = dietaRepo.findById(id);
        if(dieta.isEmpty()){
            throw new IllegalArgumentException("No existe la dieta con id " + id);
        }else{
            return dieta.get();
        }
    }

    // Actualiza la dieta con los datos de la dieta dada si existe
    public Dieta updateDieta(Dieta dieta){
        if(dietaRepo.existsById(dieta.getId())){
            var opDieta =  dietaRepo.findByNombre(dieta.getNombre());
            if(opDieta.isPresent() && opDieta.get().getId()!= dieta.getId()){
                throw new IllegalArgumentException("La dieta ya existe");
            }
            opDieta = dietaRepo.findById(dieta.getId());
            opDieta.ifPresent(n -> {
                n.setNombre(dieta.getNombre());
            });
            return dietaRepo.save(opDieta.get());
        }else{
            throw new IllegalArgumentException("Dieta no encontrada");
        }
    }   
}
