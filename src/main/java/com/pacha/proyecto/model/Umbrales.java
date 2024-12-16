package com.pacha.proyecto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "umbrales")
public class Umbrales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_umbrales")
    private Integer idUmbrales;
    @Column(name = "temp_max")
    private Float tempMax;
    @Column(name = "temp_min")
    private Float tempMin;
    @Column(name = "pcpn")
    private Float pcpn;
    @Column(name = "id_fenologia")
    private Integer idFenologia;
    @Column(name = "temp_opt_min")
    private Float tempOptMin;
    @Column(name = "umb_inf")
    private Float umbInf;
    @Column(name = "umb_sup")
    private Float umbSup;
    @Column(name = "temp_opt_max")
    private Float tempOptMax;

    @Override
    public String toString() {
        return "DatosPronostico [idUmbrales=" + idUmbrales +
                ", tempMax=" + tempMax +
                ", tempMin=" + tempMin +
                ", pcpn=" + pcpn +
                ", idFenologia=" + idFenologia +
                "]";
    }

}
