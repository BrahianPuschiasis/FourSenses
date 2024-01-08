package com.example.proyectointegrador.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDto {

    private Long userId;
    private List<Long> comidaIds;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

}
