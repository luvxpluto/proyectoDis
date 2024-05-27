package com.web.proyectoDisenno.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {
  @Getter
  @Id
  private String identificacion;
  @Getter
  private String nombreCompleto;
  @Getter
  private String correo;
  @Getter
  private String numeroTelefono;
  @Getter
  private String urlFoto;

  @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Tematica> tematicas;

  public Usuario(String identificacion, String nombreCompleto, String correo, String numeroTelefono) {
    this.identificacion = identificacion;
    this.nombreCompleto = nombreCompleto;
    this.correo = correo;
    this.numeroTelefono = numeroTelefono;
    this.tematicas = new ArrayList<>();
  }

  public void agregarTematica(Tematica tematica) {
    this.tematicas.add(tematica);
  }
}
