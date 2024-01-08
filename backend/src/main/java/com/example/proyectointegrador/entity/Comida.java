package com.example.proyectointegrador.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "comidas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String descripcion;

    @Column
    private String categoria;

    @ElementCollection
    @CollectionTable(name = "comida_imagenes", joinColumns = @JoinColumn(name = "comida_id"))
    @Column(name = "imagen_url")
    private List<String> imagenes;

  //  @OneToMany(mappedBy = "comida", cascade = CascadeType.ALL)
  //  private List<Favorito> favoritos = new ArrayList<>();




}
