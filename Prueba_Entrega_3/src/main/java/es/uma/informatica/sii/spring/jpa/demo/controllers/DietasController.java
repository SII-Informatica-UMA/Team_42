package es.uma.informatica.sii.spring.jpa.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import es.uma.informatica.sii.spring.jpa.demo.entities.Dieta;
import es.uma.informatica.sii.spring.jpa.demo.services.LogicaDietas;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dieta")
public class DietasController {

    private LogicaDietas logicaDietas;

    public DietasController(LogicaDietas logicaDietas) {
        this.logicaDietas = logicaDietas;
    }


    @GetMapping()
    public Optional<Dieta> getDietasEntrenador(@RequestParam(required = false) Integer idEntrenador, @RequestParam(required = false) Integer idCliente) {
        if(idEntrenador != null && idCliente != null) {
            throw new IllegalArgumentException("No se puede proporcionar idEntrenador e idCliente simultáneamente");
        }
        if (idEntrenador != null) {
            // idEntrenador was passed
            return logicaDietas.getDietasByEntrenadorId(idEntrenador.intValue());
        } else if (idCliente != null) {
            // idCliente was passed
            return logicaDietas.getDietasByClienteId(idCliente.intValue());
        } else {
            // Neither idEntrenador nor idCliente was passed
            throw new IllegalArgumentException("Debe proporcionar idEntrenador o idCliente");
        }
    }

    @PutMapping()
    public Dieta updateDietaCliente(@RequestBody Dieta dieta, @RequestParam Integer idCliente) {
        return logicaDietas.updateDietaCliente(dieta, idCliente);
    }

    @PostMapping()
    public ResponseEntity<Dieta> createDieta(@RequestBody Dieta dieta,@RequestParam Integer idEntrenador , UriComponentsBuilder uriBuilder) {
        Dieta dietaCreada = logicaDietas.addDieta(dieta, idEntrenador.intValue());
        return ResponseEntity.created(uriBuilder.path("/dieta/{id}").buildAndExpand(dietaCreada.getId()).toUri()).body(dietaCreada);
    }

    @GetMapping("/{id}")
    public Dieta getDieta(@PathVariable Long id) {
        return logicaDietas.getDieta(id);
    }

    @PutMapping("/{id}")
    public Dieta updateDieta(@PathVariable Long id, @RequestBody Dieta dieta) {
        return logicaDietas.updateDieta(dieta, id);
    }
    

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDieta(@PathVariable Long id) {
        logicaDietas.deleteDieta(id);
    }

}
