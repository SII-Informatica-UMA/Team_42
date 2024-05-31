package es.uma.informatica.sii.spring.jpa.demo.services;

import es.uma.informatica.sii.spring.jpa.demo.dtos.ClienteDTO;
import es.uma.informatica.sii.spring.jpa.demo.dtos.EntrenadorDTO;
import es.uma.informatica.sii.spring.jpa.demo.entities.Dieta;
import es.uma.informatica.sii.spring.jpa.demo.repositories.DietaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import es.uma.informatica.sii.spring.jpa.demo.security.JwtUtil;


@Service
@Transactional
public class LogicaDietas {
    private DietaRepository dietaRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RestTemplate restTemplate;
    

    public LogicaDietas(DietaRepository dietaRepo) {
        this.dietaRepo = dietaRepo;
    }

    // Devuelve la dieta con el id dado si existe y si el usuario autenticado es el entrenador o un cliente de la dieta
    public Dieta getDieta(Long id) {
        Optional<Dieta> optionalDieta = dietaRepo.findById(id);
        if (optionalDieta.isEmpty()) {
            throw new IllegalArgumentException("No existe la dieta con id " + id);
        }

        // Obtener el id del usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getCredentials().toString();
        int idUsuarioAutenticado = jwtUtil.extractUserId(token);

        Dieta dieta = optionalDieta.get();
        // Comprobar si el usuario autenticado es un cliente de la dieta
        for(int idCliente:dieta.getClientes()){
            var cliente = restTemplate.getForObject("http://localhost:8080/cliente/" + idCliente, ClienteDTO.class );
            if(cliente.getIdUsuario() == idUsuarioAutenticado){
                return dieta;
            }
        }

        int idEntrenador = dieta.getIDEntrenador();
        // Hacer la llamada al servicio de entrenador
        var entrenador = restTemplate.getForObject("http://localhost:8080/entrenador/" + idEntrenador, EntrenadorDTO.class );

        // Obtener el idUsuario del entrenador
        int idUsuarioEntrenador = entrenador.getIdUsuario();
        if(idUsuarioEntrenador == idUsuarioAutenticado){
            return dieta;
        }else{
            throw new IllegalArgumentException("El usuario autenticado no tiene permisos para ver la dieta");}
    }


    // Devuelve las dietas que tienen el idCliente en el array clientes[]
    public Optional<Dieta> getDietasByClienteId(int idCliente) {

        // Obtener el id del usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getCredentials().toString();
        int idUsuarioAutenticado = jwtUtil.extractUserId(token);

        Dieta dieta = dietaRepo.findDietaByClienteId(idCliente).get();
        int idEntrenador = dieta.getIDEntrenador();

        // Comprobar si el usuario autenticado es el cliente o el entrenador
        var cliente = restTemplate.getForObject("http://localhost:8080/cliente/" + idCliente, ClienteDTO.class );
        var entrenador = restTemplate.getForObject("http://localhost:8080/entrenador/" + idEntrenador, EntrenadorDTO.class );
        if(cliente.getIdUsuario() != idUsuarioAutenticado && entrenador.getIdUsuario() != idUsuarioAutenticado){
            throw new IllegalArgumentException("El usuario autenticado no es el cliente ni el entrenador");
            
        }
        return dietaRepo.findDietaByClienteId(idCliente);
    }

    // Devuelve las dietas que tiene el entrenador con el id dado
    public Optional<Dieta> getDietasByEntrenadorId(int idEntrenador) {
        
        // Obtener el id del usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getCredentials().toString();
        int idUsuarioAutenticado = jwtUtil.extractUserId(token);

        // Comprobar si el usuario autenticado es el entrenador
        var entrenador = restTemplate.getForObject("http://localhost:8080/entrenador/" + idEntrenador, EntrenadorDTO.class );
        if(entrenador.getIdUsuario() != idUsuarioAutenticado){
            throw new IllegalArgumentException("El usuario autenticado no es el entrenador");
        }
        return dietaRepo.findByIdEntrenador(idEntrenador);
    }

    // Añade una dieta a la base de datos
    public Dieta addDieta(Dieta dieta, int idEntrenador){
        dieta.setId(null);
        dieta.setIDEntrenador(idEntrenador);
    
        // Obtener el id del usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getCredentials().toString();
        int idUsuarioAutenticado = jwtUtil.extractUserId(token);

        // Comprobar si el usuario autenticado es el entrenador
        var entrenador = restTemplate.getForObject("http://localhost:8080/entrenador/" + idEntrenador, EntrenadorDTO.class );
        if(entrenador.getIdUsuario() != idUsuarioAutenticado){
            throw new IllegalArgumentException("El usuario autenticado no es el entrenador");
        }
        dietaRepo.findByNombre(dieta.getNombre()).ifPresent(n -> {
            throw new IllegalArgumentException("La dieta ya existe");
        });

        return dietaRepo.save(dieta);
    }

    // Elimina la dieta con el id dado si existe y si el entrenador que la quiere borrar es el que la creo
    public void deleteDieta(Long id){
        var dieta = dietaRepo.findById(id);

        if(dieta.isPresent()){
            
            int idEntrenador = dieta.get().getIDEntrenador();
            // Hacer la llamada al servicio de entrenador
            var entrenador = restTemplate.getForObject("http://localhost:8080/entrenador/" + idEntrenador, EntrenadorDTO.class );

            // Obtener el idUsuario del entrenador
            int idUsuario = entrenador.getIdUsuario();

            // Obtener el id del usuario autenticado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String token = authentication.getCredentials().toString();
            int idUsuarioAutenticado = jwtUtil.extractUserId(token);

            // Comprobar si el usuario autenticado es el entrenador
            if (idUsuarioAutenticado != idUsuario) {
                throw new IllegalArgumentException("El usuario autenticado no es el entrenador");
            }

            dietaRepo.deleteById(id);
        }else{
            throw new IllegalArgumentException("No existe la dieta con id " + id);
        }
    }

    // Asigna una dieta a un cliente
    public Dieta updateDietaCliente(Dieta dieta, int idCliente){
        if(dietaRepo.existsById(dieta.getId())){
        var opDieta =  dietaRepo.findByNombre(dieta.getNombre());
        if(opDieta.isPresent() && opDieta.get().getId()!= dieta.getId()){
            throw new IllegalArgumentException("Ya existe una dieta con ese nombre");
        }
        opDieta = dietaRepo.findById(dieta.getId());
        opDieta.ifPresent(n -> {
            n.setNombre(dieta.getNombre());
            // Assuming clientes is a List<Integer>
            if(n.getClientes() == null) {
                n.setClientes(new ArrayList<Integer>());
            }
            if(!n.getClientes().contains(idCliente)) {
                n.getClientes().add(idCliente);
            }
        });

        // Elimina al cliente de la otra dieta a la que estaba asignado si tuviera alguna dieta asignada
        var otherDieta = dietaRepo.findDietaByClienteId(idCliente);
        if(otherDieta.isPresent()) {
            otherDieta.ifPresent(n -> {
                n.getClientes().remove(idCliente);
            });
        }

        return dietaRepo.save(opDieta.get());
        }else{
            throw new IllegalArgumentException("Dieta no encontrada");
        }
    }
    
    // Actualiza la dieta con el id dado si existe y si el usuario autenticado es el entrenador
    public Dieta updateDieta(Dieta dieta, Long id){
        if(dietaRepo.existsById(id)){
            var opDieta =  dietaRepo.findById(id);

            // Obtener el id del usuario autenticado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String token = authentication.getCredentials().toString();
            int idUsuarioAutenticado = jwtUtil.extractUserId(token);

            int idEntrenador = opDieta.get().getIDEntrenador();
            // Comprobar si el usuario autenticado es el entrenador
            var entrenador = restTemplate.getForObject("http://localhost:8080/entrenador/" + idEntrenador, EntrenadorDTO.class );
            if(entrenador.getIdUsuario() != idUsuarioAutenticado){
                throw new IllegalArgumentException("El usuario autenticado no es el entrenador");
            }
                
            if(opDieta.isPresent() && opDieta.get().getId()!= dieta.getId()){
                throw new IllegalArgumentException("Ya existe una dieta con ese nombre");
            
            }
            
            opDieta = dietaRepo.findById(dieta.getId());
            opDieta.ifPresent(n -> {
                n.setNombre(dieta.getNombre());
                n.setDescripcion(dieta.getDescripcion());
                n.setObjetivo(dieta.getObjetivo());
                n.setDuracionDias(dieta.getDuracionDias());
                n.setAlimentos(dieta.getAlimentos());
                n.setRecomendaciones(dieta.getRecomendaciones());
                n.setIDEntrenador(dieta.getIDEntrenador());
                n.setClientes(dieta.getClientes());
            });
            return dietaRepo.save(opDieta.get());
        }else{
            throw new IllegalArgumentException("Dieta no encontrada");
        }
    }
}
