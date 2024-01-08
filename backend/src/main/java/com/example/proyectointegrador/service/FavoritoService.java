package com.example.proyectointegrador.service;

import com.example.proyectointegrador.entity.Comida;
import com.example.proyectointegrador.entity.Favorito;
import com.example.proyectointegrador.repository.FavoritoRepository;
import com.example.proyectointegrador.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FavoritoService {
    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private ComidaService comidaService;

    @Autowired
    public FavoritoService(FavoritoRepository favoritoRepository) {
        this.favoritoRepository = favoritoRepository;
    }

    public Optional<Favorito> obtenerFavoritoPorId(Long favoritoId) {
        return favoritoRepository.findById(favoritoId);
    }
    public List<Favorito> obtenerTodosLosFavoritos() {
        return favoritoRepository.findAll();
    }

    public Favorito guardarFavorito(Favorito favorito) {
        return favoritoRepository.save(favorito);
    }

    public void eliminarFavorito(Long favoritoId) {
        favoritoRepository.deleteById(favoritoId);
    }


    public List<Comida> obtenerFechasReservasPorComidaId(Integer userID) {
        List<Long> idsComidas = favoritoRepository.encontrarFavoritoUsuarioID(userID);

        List<Comida> comidas = new ArrayList<>();
        for (Long idComida : idsComidas) {
            comidaService.buscarComidaPorId(idComida).ifPresent(comidas::add);
        }

        return comidas;
    }


    public void eliminarFavoritoPorComidaYUsuario(Long comidaId, Long usuarioId) {
       favoritoRepository.eliminarFavoritoPorComidaYUsuario(comidaId, usuarioId);
    }

public void eliminarComidas (Long comidaId){

    favoritoRepository.eliminarComidas(comidaId);
}


}
