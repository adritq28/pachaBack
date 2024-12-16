package com.pacha.proyecto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pacha.proyecto.model.DatosEstacionMeteorologica;
import com.pacha.proyecto.repository.DatosEstacionMeteorologicaRepository;

@CrossOrigin(origins = "*")
@RequestMapping("/datosEstacion")

@RestController
public class DatosEstacionMeteorologicaController {

    @Autowired
    private DatosEstacionMeteorologicaRepository datosEstacionRepository;

    @PostMapping("/addDatosEstacion")
    public ResponseEntity<String> guardarDatosEstacion(@RequestBody DatosEstacionMeteorologica datosEstacion) {
        try {
            System.out.println("--+++++--" + datosEstacion.toString());
            DatosEstacionMeteorologica nuevodatosEstacion = datosEstacionRepository.save(datosEstacion);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("datos estacion guardada con ID: " + nuevodatosEstacion.getIdDatosEst());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la datos estacion: " + e.getMessage());
        }
    }

    @PutMapping("/updateDatosEstacion/{id}")
    public ResponseEntity<String> actualizarDatosEstacion2(@PathVariable("id") int id,
            @RequestBody DatosEstacionMeteorologica datosEstacionActualizados) {
        try {
            Optional<DatosEstacionMeteorologica> datosEstacionExistente2 = datosEstacionRepository.findById(id);
            if (datosEstacionExistente2 != null) {
                DatosEstacionMeteorologica datosEstacion = datosEstacionExistente2.get();
                datosEstacion.setTempMax(datosEstacionActualizados.getTempMax());
                datosEstacion.setTempMin(datosEstacionActualizados.getTempMin());
                datosEstacion.setTempAmb(datosEstacionActualizados.getTempAmb());
                datosEstacion.setPcpn(datosEstacionActualizados.getPcpn());
                datosEstacion.setTaevap(datosEstacionActualizados.getTaevap());
                datosEstacion.setFechaReg(datosEstacionActualizados.getFechaReg());
                datosEstacion.setDirViento(datosEstacionActualizados.getDirViento());
                datosEstacion.setVelViento(datosEstacionActualizados.getVelViento());
                datosEstacion.setIdEstacion(datosEstacionActualizados.getIdEstacion());
                DatosEstacionMeteorologica datosEstacionActualizada = datosEstacionRepository.save(datosEstacion);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Datos de la estación actualizados con éxito. ID: "
                                + datosEstacionActualizada.getIdDatosEst());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró la estación con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar los datos de la estación: " + e.getMessage());
        }
    }

}
