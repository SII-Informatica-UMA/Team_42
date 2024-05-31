package es.uma.informatica.sii.spring.jpa.demo;

import es.uma.informatica.sii.spring.jpa.demo.entities.Dieta;
import es.uma.informatica.sii.spring.jpa.demo.repositories.DietaRepository;
import es.uma.informatica.sii.spring.jpa.demo.security.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import es.uma.informatica.sii.spring.jpa.demo.dtos.*;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("En el servicio de Dietas")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

class DietaTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private DietaRepository dietaRepo;

    @Autowired
    private JwtUtil jwtUtil;
    private UserDetails userDetails;
    private String token;

    @BeforeEach
    public void initializeDatabase() {
        dietaRepo.deleteAll();
        //userDetails = jwtUtil.createUserDetails("1", "", List.of("ROLE_USER"));
    }

    /* public UserDetails createUserDetails(String username, String password, List<String> roles) {
        List<SimpleGrantedAuthority> authorities = roles.stream()
            .map(SimpleGrantedAuthority::new)
            .toList();
        return new User(username, password, authorities);
    }*/

    private URI uri(String scheme, String host, int port, String... paths) {
        UriBuilderFactory ubf = new DefaultUriBuilderFactory();
        UriBuilder ub = ubf.builder()
            .scheme(scheme)
            .host(host).port(port);
        for (String path : paths) {
            ub = ub.path(path);
        }
        return ub.build();
    }

    private RequestEntity<Void> get(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + token)
            .build();
        System.out.println("TOKEN :::  " + token);
        return peticion;
    }

    private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.delete(uri)
            .build();
        return peticion;
    }

    private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(object);
        return peticion;
    }

    private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(object);
        return peticion;
    }
    
    private RequestEntity<Void> putWithParameters(String scheme, String host, int port, String path, Map<String, Integer> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(scheme + "://" + host + ":" + port + path);
        queryParams.forEach(builder::queryParam);

        URI uri = builder.build().encode().toUri();

        return RequestEntity.delete(uri).build();
    }

    @Nested 
    @DisplayName("Cuando no hay ninguna dieta")
    public class DietasVacías {
        
        public class DietasVaciasTest {
            @Test
            @DisplayName("devuelve una lista de dietas vacía")
            public void testGetDietas() {
                var peticion = get("http", "localhost", port, "/dieta");

                var respuesta = restTemplate.exchange(peticion,
                    new ParameterizedTypeReference<List<Dieta>>() {
                    });

                assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
                assertThat(respuesta.getBody()).isEmpty();
            }

            @Test
            @DisplayName("intenta modificar una dieta cuando la lista esta vacia")
            public void testPutDietas() {
                Map<String, Integer> queryParams = new HashMap<>();
                queryParams.put("idDietas", 1);
            
                var peticion = putWithParameters("http", "localhost", port, "/dieta", queryParams);

			    var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<DietaDTO>() {});

			    assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
            }

            @Test
            @DisplayName("intenta crear una dieta cuando la lista esta vacia")
            public void testPostDietas() {
                // Preparamos la dieta a insertar
                var dieta = DietaDTO.builder()
                .nombre("Adelgazamiento")
                .build();
                // Preparamos la petición con la dieta dentro
                var peticion = post("http", "localhost",port, "/dieta", dieta);

                // Invocamos al servicio REST 
                var respuesta = restTemplate.exchange(peticion,Void.class);

                // Comprobamos el resultado
                assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
                assertThat(respuesta.getHeaders().get("Location").get(0))
                .startsWith("http://localhost:" + port + "/dieta");

                List<Dieta> dietasBD = dietaRepo.findAll();
                assertThat(dietasBD).hasSize(1);
                assertThat(respuesta.getHeaders().get("Location").get(0))
                .endsWith("/" + dietasBD.get(0).getId());
                assertThat(dieta.getNombre()).isEqualTo(dietasBD.get(0).getNombre());
            }

            @Test
            @DisplayName("intenta modificar una dieta cuando la lista esta vacia")
            public void testPutDietasById() {
                var dieta = DietaDTO.builder()
                .nombre("Adelgazamiento")
                .build();
            
                var peticion = put("http", "localhost",port, "/dieta/1", dieta);

			    var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<DietaDTO>() {});

			    assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
            }

            @Test
        @DisplayName("devuelve una lista de dietas vacía")
        public void testGetDieta() {
            var peticion = get("http", "localhost", port, "/dieta");

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<List<Dieta>>() {
                });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody()).isEmpty();
        }

        @Test
        @DisplayName("no puede devolver una dieta concreta")
        public void testGetDietaById() {
            var peticion = get("http", "localhost", port, "/dieta/1");

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<List<DietaDTO>>() {
                });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(400);
            assertThat(respuesta.getBody()).isEmpty();
        }

        @Test
        @DisplayName("no puede eliminar una dieta concreta")
        public void testDeleteDietaById() {
            var peticion = delete("http", "localhost", port, "/dieta/1");

            var respuesta = restTemplate.exchange(peticion, Void.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
            assertThat(dietaRepo.count()).isEqualTo(1);
        }

        @Test
        @DisplayName("devuelve error al acceder a una dieta concreta")
        public void errorConDietaConcreta(){
            var peticion = get("http", "localhost",port, "/dieta/1");
			
			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<DietaDTO>() {});
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        }
    }
}