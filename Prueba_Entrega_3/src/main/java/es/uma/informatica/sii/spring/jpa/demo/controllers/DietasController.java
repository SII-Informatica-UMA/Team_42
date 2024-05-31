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

    @GetMapping
    public List<Dieta> getDietas(@RequestParam Long idEntrenador) {
        return logicaDietas.getDietas();
    }

    @GetMapping("/{id}")
    public Optional<Dieta> getDietasEntrenador(@PathVariable Long id, @RequestParam(required = false) Integer idEntrenador, @RequestParam(required = false) Integer idCliente) {
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

    @PostMapping()
    public ResponseEntity<Dieta> addDieta(@RequestBody Dieta dieta, UriComponentsBuilder builder) {
        var dietaCreada = logicaDietas.addDieta(dieta, dieta.getIDEntrenador());
        return ResponseEntity.created(builder.path("/dieta/{id}").buildAndExpand(dietaCreada.getId()).toUri()).body(dietaCreada);
    }

    @GetMapping("/{id}")
    public Dieta getDieta(@PathVariable Long id) {
        return logicaDietas.getDieta(id);
    }

    /*@PutMapping()
    public Dieta updateDieta(@RequestBody Dieta dieta, @RequestParam Long idCliente) {
        return logicaDietas.updateDieta(dieta, idCliente);
    }*/

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDieta(@PathVariable Long id) {
        logicaDietas.deleteDieta(id);
    }

}
