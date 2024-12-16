package com.pacha.proyecto.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "datos_pronostico")
public class DatosPronostico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pronostico")
    private Integer idPronostico;
    @Column(name = "temp_max")
    private Float tempMax;
    @Column(name = "temp_min")
    private Float tempMin;
    @Column(name = "pcpn")
    private Float pcpn;
    @Column(name = "id_zona")
    private Integer idZona;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date fecha = new Date(System.currentTimeMillis());
    @Column(name = "delete")
    private Boolean delete = false;
    @Column(name = "edit")
    private Boolean edit = false;

    @Column(name = "fecha_rango_decenal")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date fechaRangoDecenal = new Date(System.currentTimeMillis());
    @Column(name = "id_cultivo")
    private Integer idCultivo;

    @Override
    public String toString() {
        return "DatosPronostico [idPronostico=" + idPronostico +
                ", tempMax=" + tempMax +
                ", tempMin=" + tempMin +
                ", pcpn=" + pcpn +
                ", idZona=" + idZona +
                ", fecha=" + fecha +
                ", delete=" + delete +
                ", fechaRangoDecenal=" + fechaRangoDecenal +
                ", idCultivo=" + idCultivo +
                "]";
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

}
