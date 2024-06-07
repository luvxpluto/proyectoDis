package com.web.proyectoDisenno.service;

import com.web.proyectoDisenno.model.Bitacora;
import com.web.proyectoDisenno.repository.BitacoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;

@Service
public class BitacoraService {
  private final BitacoraRepository bitacoraRepository;

  @Autowired
  public BitacoraService(BitacoraRepository bitacoraRepository) {
    this.bitacoraRepository = bitacoraRepository;
  }

  public String getBitacorasByTipo(String tipo) {
    ArrayList<Bitacora> bitacoras = (ArrayList<Bitacora>) bitacoraRepository.findByTipo(tipo);
    return switch (tipo) {
      case "XML" -> bitacoraXML(bitacoras);
      case "CSV" -> bitacoraCSV(bitacoras);
      case "TP" -> bitacoraTrama(bitacoras);
      default -> "Tipo de bitacora no soportado";
    };
  }

  public String getBitacorasByTipoAndUsuarioIdentificacion(String tipo, String identificacion) {
    ArrayList<Bitacora> bitacoras = (ArrayList<Bitacora>) bitacoraRepository.findByTipoAndUsuarioIdentificacion(tipo, identificacion);
    return switch (tipo) {
      case "XML" -> bitacoraXML(bitacoras);
      case "CSV" -> bitacoraCSV(bitacoras);
      case "TP" -> bitacoraTrama(bitacoras);
      default -> "Tipo de bitacora no soportado";
    };
  }

  public String getBitacorasByTipoAndFechaHoy(String tipo, String identificacion) {
    ArrayList<Bitacora> bitacoras = (ArrayList<Bitacora>) bitacoraRepository.findByFechaAndTipo(LocalDate.now(), tipo, identificacion);
    return switch (tipo) {
      case "XML" -> bitacoraXML(bitacoras);
      case "CSV" -> bitacoraCSV(bitacoras);
      case "TP" -> bitacoraTrama(bitacoras);
      default -> "Tipo de bitacora no soportado";
    };
  }

  public String getBitacorasEntreHorasPorTipo(LocalTime horaInicio, LocalTime horaFin, String tipo, String identificacion) {
    ArrayList<Bitacora> bitacoras = (ArrayList<Bitacora>) bitacoraRepository.findBitacorasEntreHorasPorTipo(LocalDate.now(),horaInicio, horaFin, tipo, identificacion);
    return switch (tipo) {
      case "XML" -> bitacoraXML(bitacoras);
      case "CSV" -> bitacoraCSV(bitacoras);
      case "TP" -> bitacoraTrama(bitacoras);
      default -> "Tipo de bitacora no soportado";
    };
  }

  public void saveBitacora(Bitacora bitacora) {
    bitacoraRepository.save(bitacora);
  }

  private String bitacoraXML(ArrayList<Bitacora> bitacoras) {
    StringBuilder xml = new StringBuilder();
    xml.append("<bitacoras>\n");
    for (Bitacora bitacora : bitacoras) {
      xml.append(bitacora.getDescripcion());
      xml.append("\n");
    }
    xml.append("\n</bitacoras>");
    return xml.toString();
  }

  private String bitacoraCSV(ArrayList<Bitacora> bitacoras) {
    StringBuilder csv = new StringBuilder();
    csv.append("acción,usuario,fecha,hora,ip,sistemaOperativo\n");
    for (Bitacora bitacora : bitacoras) {
      csv.append(bitacora.getDescripcion());
      csv.append("\n");

    }
    return csv.toString();
  }

  private String bitacoraTrama(ArrayList<Bitacora> bitacoras) {
    StringBuilder trama = new StringBuilder();
    for (Bitacora bitacora : bitacoras) {
      trama.append(bitacora.getDescripcion());
      trama.append("\n");
    }
    return trama.toString();
  }
}
