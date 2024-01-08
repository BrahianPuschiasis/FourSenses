package com.example.proyectointegrador.repository;

import com.example.proyectointegrador.entity.Comida;
import com.example.proyectointegrador.entity.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    Optional<Favorito> findById(Long id);


    @Query(value ="SELECT comidas.id FROM comidas JOIN favoritos WHERE favoritos.id_comida = comidas.id and favoritos.id_usuario =  :userID", nativeQuery = true)
    List<Long> encontrarFavoritoUsuarioID(@Param("userID") Integer userID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM favoritos WHERE id_comida = :comidaId AND id_usuario = :usuarioId", nativeQuery = true)
    void eliminarFavoritoPorComidaYUsuario(@Param("comidaId") Long comidaId, @Param("usuarioId") Long usuarioId);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM favoritos WHERE id_comida = :comidaId", nativeQuery = true)
    void eliminarComidas(@Param("comidaId") Long comidaId);


}
