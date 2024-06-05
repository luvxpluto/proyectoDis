package com.web.proyectoDisenno.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

public abstract class UserAction {
    @Setter
    private String accion;
    protected List<Bitacora> bitacoras;

    public void notifyBitacoras(String accion) {
        for (Bitacora bitacora : bitacoras) {
            bitacora.registrar(accion);
        }
    }

    public void attachBitacora(Bitacora bitacora) {
        bitacoras.add(bitacora);
    }

    public void detachBitacora(Bitacora bitacora) {
        bitacoras.remove(bitacora);
    }
}
