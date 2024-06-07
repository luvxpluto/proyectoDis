package com.web.proyectoDisenno.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.web.proyectoDisenno.service.BitacoraService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bitacoras")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Bitacora {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @Setter
  @JoinColumn(name = "creado_por", nullable = false)
  protected Usuario usuario;

  @Getter
  @JoinColumn(name = "fecha")
  protected LocalDate fecha;

  @Getter
  @JoinColumn(name = "hora")
  protected LocalTime hora;

  @Getter
  @JoinColumn(name = "descripcion")
  protected String descripcion;

  @Setter
  @Transient
  protected BitacoraService service;

  @Getter
  @Column(insertable = false, updatable = false)
  private String tipo;

  public Bitacora(Usuario usuario) {
    this.usuario = usuario;
  }

  public abstract void registrar(String accion);

  protected String getIP() {
    try {
      InetAddress localHost = InetAddress.getLocalHost();
      return localHost.getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
      return "Unknown";
    }
  }

  protected String getSistemaOperativo(){
    return System.getProperty("os.name");
  }
}