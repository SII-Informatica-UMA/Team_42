package es.uma.informatica.sii.spring.jpa.demo.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietaNuevaDTO {
    private String nombre;
    private String descripcion;
    private String observaciones;
    private String objetivo;
    private Integer duracionDias;
    private String[] alimentos;
    private String recomendaciones;
}
