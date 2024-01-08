package com.example.proyectointegrador.controller;

import com.example.proyectointegrador.dto.ReservaDto;
import com.example.proyectointegrador.entity.Comida;
import com.example.proyectointegrador.repository.ReservaRepository;
import com.example.proyectointegrador.service.ComidaService;
import com.example.proyectointegrador.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


@RestController
@CrossOrigin
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;


    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ComidaService comidaService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> createReserva(@RequestBody ReservaDto reservaDto) {
        try {
            reservaService.crearReserva(reservaDto);
            return new ResponseEntity<>("Reserva creada con éxito", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la reserva: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GetMapping("/{comidaID}")
    public List<List<LocalDate>> obtenerFechasReservasPorComidaId(@PathVariable Integer comidaID) {
        return reservaService.obtenerFechasReservasPorComidaId(comidaID);
    }


    @GetMapping("/buscar")
    public List<Comida> buscarComidas(@RequestParam(required = false) String nombre, @RequestParam(required = false) String categoria, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        List<Object[]> result = reservaRepository.findComidas(nombre, categoria, fechaInicio, fechaFin);

        Map<Long, Comida> comidasMap = new LinkedHashMap<>();
        for (Object[] row : result) {
            Long id = (Long) row[0];
            Comida comida = comidasMap.getOrDefault(id, new Comida());
            comida.setId(id);
            comida.setNombre((String) row[3]);
            comida.setDescripcion((String) row[2]);
            comida.setCategoria((String) row[1]);
            List<String> imagenes = comida.getImagenes();
            if (imagenes == null) {
                imagenes = new ArrayList<>();
                comida.setImagenes(imagenes);
            }
            imagenes.add((String) row[4]);
            comidasMap.put(id, comida);
        }

        return new ArrayList<>(comidasMap.values());
    }



    @GetMapping("/historial/{userId}")
    public List<Object[]> getHistorial(@PathVariable Long userId) {
        return reservaRepository.historialReservas(userId);
    }


}
