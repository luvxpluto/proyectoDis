package com.web.proyectoDisenno.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tematicas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombre", "creado_por"})
})
public class Tematica {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Column(nullable = false)
  private String nombre;

  @Getter
  private String descripcion;

  @Getter
  private String urlImagen;

  @ManyToOne
  @JoinColumn(name = "creado_por", nullable = false)
  private Usuario usuario;

  @OneToMany(mappedBy = "tematica", cascade = CascadeType.REMOVE)
  private List<Texto> textos;

  public Tematica(String nombre, String descripcion, Usuario usuario, String urlImagen) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.usuario = usuario;
    this.urlImagen = urlImagen;
    this.textos = new ArrayList<>();
  }

  public void agregarTexto(Texto texto) {
    this.textos.add(texto);
  }
}
