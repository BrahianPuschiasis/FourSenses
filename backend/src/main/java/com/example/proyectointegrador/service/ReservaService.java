package com.example.proyectointegrador.service;

import com.example.proyectointegrador.dto.ReservaDto;
import com.example.proyectointegrador.entity.Comida;
import com.example.proyectointegrador.entity.Reserva;
import com.example.proyectointegrador.repository.ComidaRepository;
import com.example.proyectointegrador.repository.ReservaRepository;
import com.example.proyectointegrador.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComidaRepository comidaRepository;

    public void crearReserva(ReservaDto reservaDto) {
        Reserva reserva = new Reserva();

        // Setear atributos de Reserva desde ReservaDto
        reserva.setUser(userRepository.findById(reservaDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado")));

        // Obtener las comidas desde el repositorio
        List<Comida> comidas = comidaRepository.findAllById(reservaDto.getComidaIds());
        reserva.setComidas(comidas);

        // Setear la fecha de reserva
        LocalDate fechaInicio = reservaDto.getFechaInicio();
        LocalDate fechaFin = reservaDto.getFechaFin();

        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        // Verificar que la diferencia entre fechaInicio y fechaFin sea al menos 2 días
        if (ChronoUnit.DAYS.between(fechaInicio, fechaFin) < 1) {
            throw new IllegalArgumentException("La reserva debe ser de al menos 2 días");
        }

        // Verificar si hay reservas solapadas
        if (reservaRepository.countReservasSolapadas(Math.toIntExact(reservaDto.getComidaIds().get(0)), fechaInicio, fechaFin) > 0) {
            throw new IllegalArgumentException("Ya existe una reserva en el rango de fechas proporcionado para la comida seleccionada");
        }



        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);

        // Guardar la reserva
        reservaRepository.save(reserva);
    }



    public List<List<LocalDate>> obtenerFechasReservasPorComidaId(Integer comidaID) {
        List<Object[]> result = reservaRepository.findFechasReservasPorComidaId(comidaID);

        return result.stream()
                .map(row -> {
                    LocalDate fechaInicio = ((java.sql.Date) row[0]).toLocalDate();
                    LocalDate fechaFin = ((java.sql.Date) row[1]).toLocalDate();

                    // Agregar ambas fechas a la lista
                    return Arrays.asList(fechaInicio, fechaFin);
                })
                .collect(Collectors.toList());
    }




    public void eliminarReserva (Long comidaId){

        reservaRepository.eliminarReserva(comidaId);
    }



}
