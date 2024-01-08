package com.example.proyectointegrador.repository;

import com.example.proyectointegrador.entity.Comida;
import com.example.proyectointegrador.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    Optional<Reserva> findById(Integer id);

    @Query(value ="SELECT fecha_inicio, fecha_fin FROM reservas JOIN reserva_comida ON reservas.id = reserva_comida.reserva_id WHERE reserva_comida.comida_id = :comidaID", nativeQuery = true)
    List<Object[]> findFechasReservasPorComidaId(@Param("comidaID") Integer comidaID);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reserva_comida WHERE comida_id  = :comidaId", nativeQuery = true)
    void eliminarReserva(@Param("comidaId") Long comidaId);


    @Query(value = "SELECT COUNT(*) FROM reservas r JOIN reserva_comida rc ON r.id = rc.reserva_id WHERE rc.comida_id = :comidaID AND (r.fecha_inicio <= :fechaFin AND r.fecha_fin >= :fechaInicio)", nativeQuery = true)
    int countReservasSolapadas(@Param("comidaID") Integer comidaID, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);


    @Query(value = "SELECT c.*, ci.imagen_url FROM comidas c LEFT JOIN comida_imagenes ci ON c.id = ci.comida_id WHERE (:nombre IS NULL OR c.nombre LIKE CONCAT('%', :nombre, '%') OR c.descripcion LIKE CONCAT('%', :nombre, '%')) AND (:categoria IS NULL OR c.categoria = :categoria) AND NOT EXISTS (SELECT 1 FROM reserva_comida rc JOIN reservas r ON rc.reserva_id = r.id WHERE c.id = rc.comida_id AND ((r.fecha_inicio <= :fechaFin AND r.fecha_fin >= :fechaInicio) OR (r.fecha_inicio >= :fechaInicio AND r.fecha_inicio <= :fechaFin) OR (r.fecha_fin >= :fechaInicio AND r.fecha_fin <= :fechaFin))) ORDER BY c.id ASC", nativeQuery = true)
    List<Object[]> findComidas(@Param("nombre") String nombre, @Param("categoria") String categoria, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);


    @Query(value = "SELECT c.id, c.nombre, r.fecha_inicio, r.fecha_fin,r.inserted_at FROM comidas c JOIN reserva_comida rc ON c.id = rc.comida_id JOIN reservas r ON rc.reserva_id = r.id WHERE r.user_id = :userId ORDER BY r.inserted_at ASC", nativeQuery = true)
    List<Object[]> historialReservas(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE reservas ADD COLUMN IF NOT EXISTS inserted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nativeQuery = true)
    void addColumnInsertedAt();



}
