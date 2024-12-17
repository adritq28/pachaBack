package com.pacha.proyecto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pacha.proyecto.model.DatosPronostico;

public interface DatosPronosticoRepository extends JpaRepository<DatosPronostico, Integer> {

        Optional<DatosPronostico> findByIdPronostico(Integer id);

        List<DatosPronostico> findByIdCultivo(int idCultivo);

        // @Query(value = "SELECT temp_max, temp_min, pcpn, fecha, fecha_rango_decenal "
        // +
        // "FROM ( " +
        // "SELECT p.temp_max, p.temp_min, p.pcpn, p.fecha, p.fecha_rango_decenal " +
        // "FROM cultivo c " +
        // "JOIN datos_pronostico p ON c.id_cultivo = p.id_cultivo " +
        // "WHERE c.id_cultivo = :idCultivo " +
        // "ORDER BY p.id_pronostico DESC " +
        // "LIMIT 10 " +
        // ") AS subquery " +
        // "ORDER BY subquery.fecha_rango_decenal ASC", nativeQuery = true)
        // List<Object[]> pronosticoCultivo(@Param("idCultivo") int idCultivo);

        @Query(value = "SELECT temp_max, temp_min, pcpn, fecha, fecha_rango_decenal " +
                        "FROM datos_pronostico " +
                        "WHERE id_cultivo = :idCultivo " +
                        "ORDER BY fecha DESC, id_pronostico ASC " +
                        "LIMIT 10", nativeQuery = true)
        List<Object[]> pronosticoCultivo(@Param("idCultivo") int idCultivo);

        @Query("select z.idZona, m.nombre, z.nombre from Municipio m join Zona z on m.idMunicipio=z.idMunicipio")
        List<Object[]> listaZonas();

        @Query("select d.tempMax, d.tempMin, d.pcpn, d.fecha, d.idPronostico, d.delete, d.fechaRangoDecenal " +
                        "from DatosPronostico d join Cultivo c on d.idCultivo = c.idCultivo " +
                        "where c.idCultivo = :idCultivo order by d.fecha desc")
        List<Object[]> listaDatosZona(@Param("idCultivo") int idZona);

        @Query("SELECT COUNT(d) FROM DatosPronostico d WHERE FUNCTION('DATE', d.fecha) = CURRENT_DATE AND d.idZona = :idZona")
        int countDatosHoy(@Param("idZona") int idZona);

}
