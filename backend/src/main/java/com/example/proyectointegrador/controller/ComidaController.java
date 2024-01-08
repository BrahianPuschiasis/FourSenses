package com.example.proyectointegrador.controller;

import com.example.proyectointegrador.entity.Comida;
import com.example.proyectointegrador.exception.BadRequestException;
import com.example.proyectointegrador.exception.ComidaDuplicadaException;
import com.example.proyectointegrador.exception.ResoucerNotFoundException;
import com.example.proyectointegrador.service.ComidaService;
import com.example.proyectointegrador.service.FavoritoService;
import com.example.proyectointegrador.service.ReservaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/comidas")
@CrossOrigin
public class ComidaController {

    private final ComidaService comidaService;


    @Autowired
    public ComidaController(ComidaService comidaService) {
        this.comidaService = comidaService;
    }

    @Autowired
    public FavoritoService favoritoService;

    @Autowired
    public ReservaService reservaService;

    @GetMapping("/{id}")
    public ResponseEntity<Comida> buscarComidaPorId(@PathVariable("id") Long id) throws ResoucerNotFoundException   {
        Optional<Comida> comidaBuscada = comidaService.buscarComidaPorId(id);
        if (comidaBuscada.isPresent()) {
            return ResponseEntity.ok(comidaBuscada.get());
        } else {
            throw new ResoucerNotFoundException("Comida no encontrada con ID: " + id);
        }
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<Comida>> buscarComidasPorCategoria(@RequestParam("categoria") String categoria) throws ResoucerNotFoundException {
        List<Comida> comidas = comidaService.buscarComidasPorCategoria(categoria);
        if (comidas.isEmpty()) {
            throw new ResoucerNotFoundException("No se encontraron comidas en la categoría: " + categoria);
        }
        return ResponseEntity.ok(comidas);
    }


    @GetMapping
    public ResponseEntity<List<Comida>> listarComidas() {
        return ResponseEntity.ok(comidaService.listarTodasLasComidas());
    }


    @PostMapping("/guardar")
    @Transactional
    public ResponseEntity<Comida> guardarComida(@RequestBody Comida comida) {
        List<String> listaDeEnlaces = comida.getImagenes();
        try {
            Comida comidaGuardada = comidaService.guardarComida(comida, listaDeEnlaces);
            return ResponseEntity.ok(comidaGuardada);
        } catch (ComidaDuplicadaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, String>> eliminarComida(@PathVariable Long id) throws ResoucerNotFoundException {
        Optional<Comida> comidaBuscada = comidaService.buscarComidaPorId(id);
        if (comidaBuscada.isPresent()) {
            favoritoService.eliminarComidas(id);
            reservaService.eliminarReserva(id);
            comidaService.eliminarComida(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Comida eliminada con éxito");
            return ResponseEntity.ok(response);
        } else {
            throw new ResoucerNotFoundException("No existe el id asociado a una comida en la base de datos " + id);
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> actualizarComida(@RequestBody  Comida comida) throws BadRequestException {
        Optional<Comida> comidaBuscada = comidaService.buscarComidaPorId(comida.getId());
        if (comidaBuscada.isPresent()) {
            comidaService.actualizarComida(comida);
            return ResponseEntity.ok("Comida actualizada " + comida.getNombre());
        } else {
            throw new BadRequestException("Comida no encontrada " + comida.getNombre());
        }
    }




}
