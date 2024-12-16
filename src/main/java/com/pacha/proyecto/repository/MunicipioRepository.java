package com.pacha.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pacha.proyecto.model.Municipio;

public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {

        @Query("SELECT m.idMunicipio, m.nombre, z.idZona, z.nombre, c.nombre, c.idCultivo, z.latitud, z.longitud, c.nombreFechaSiembra "
                        +
                        "FROM Municipio m JOIN Zona z ON m.idMunicipio = z.idMunicipio " +
                        "JOIN Cultivo c ON z.idZona = c.idZona WHERE m.idMunicipio = :idMunicipio")
        List<Object[]> obtenerZonasMunicipio(@Param("idMunicipio") int idMunicipio);

        @Query("select m.nombre, m.idMunicipio from Municipio m ")
        List<Object[]> listaMunicipio();

}
