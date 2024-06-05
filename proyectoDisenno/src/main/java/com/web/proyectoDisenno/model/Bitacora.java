package com.web.proyectoDisenno.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.mapping.Join;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bitacoras")
public abstract class Bitacora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creado_por", nullable = false)
    protected Usuario usuario;

    @Getter
    @JoinColumn(name = "fecha")
    private LocalDateTime fecha;

    @Getter
    @JoinColumn(name = "tipo")
    private String tipoBitacora;

    @Getter
    @JoinColumn(name = "descripcion")
    protected String descripcion;

    public Bitacora(Usuario usuario) {
        this.usuario = usuario;
    }

    public abstract void registrar(String accion);

    public String getIP() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    public String getSistemaOperativo(){
        return System.getProperty("os.name");
    }
}