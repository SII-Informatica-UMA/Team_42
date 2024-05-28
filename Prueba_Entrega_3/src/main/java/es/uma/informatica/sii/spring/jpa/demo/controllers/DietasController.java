package es.uma.informatica.sii.spring.jpa.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import es.uma.informatica.sii.spring.jpa.demo.entities.Dieta;
import es.uma.informatica.sii.spring.jpa.demo.services.LogicaDietas;
import java.util.List;

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

    @PostMapping()
    public ResponseEntity<Dieta> addDieta(@RequestBody Dieta dieta, UriComponentsBuilder builder) {
        var dietaCreada = logicaDietas.addDieta(dieta, dieta.getIDEntrenador());
        return ResponseEntity.created(builder.path("/dieta/{id}").buildAndExpand(dietaCreada.getId()).toUri()).body(dietaCreada);
    }

    @GetMapping("/{id}")
    public Dieta getDieta(@PathVariable Long id) {
        return logicaDietas.getDieta(id);
    }

    @PutMapping("/{id}")
    public Dieta updateDieta(@PathVariable Long id, @RequestBody Dieta dieta) {
        dieta.setId(id);
        return logicaDietas.updateDieta(dieta);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDieta(@PathVariable Long id) {
        logicaDietas.deleteDieta(id);
    }

}
