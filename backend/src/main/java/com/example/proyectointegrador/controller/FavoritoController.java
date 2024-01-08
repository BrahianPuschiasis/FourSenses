package com.example.proyectointegrador.controller;

import com.example.proyectointegrador.entity.Comida;
import com.example.proyectointegrador.entity.Favorito;
import com.example.proyectointegrador.repository.FavoritoRepository;
import com.example.proyectointegrador.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favoritos")
@CrossOrigin
public class FavoritoController {

    @Autowired
    public   FavoritoService favoritoService;

    @Autowired
    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }


    @Autowired
    public FavoritoRepository favoritoRepository;

    @GetMapping("/{userID}")
    public ResponseEntity<List<Comida>> obtenerFechasReservasPorComidaId(@PathVariable Integer userID) {
        List<Comida> comidas = favoritoService.obtenerFechasReservasPorComidaId(userID);

        if (comidas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(comidas, HttpStatus.OK);
    }



    @GetMapping
    public List<Favorito> buscarTodosLosFavoritos() {
        return favoritoService.obtenerTodosLosFavoritos();
    }

    @PostMapping
    public Favorito guardarFavorito(@RequestBody Favorito favorito) {
        return favoritoService.guardarFavorito(favorito);
    }

    @DeleteMapping("/{comidaId}/{usuarioId}")
    public ResponseEntity<String> eliminarFavoritoPorComidaYUsuario(@PathVariable Long comidaId, @PathVariable Long usuarioId) {
        try {
            favoritoService.eliminarFavoritoPorComidaYUsuario(comidaId, usuarioId);
            return ResponseEntity.ok("Favorito eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el favorito.");
        }
    }
}

