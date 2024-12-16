package com.pacha.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pacha.proyecto.model.DatosEstacionHidrologica;
import com.pacha.proyecto.repository.DatosEstacionHidrologicaRepository;

@CrossOrigin(origins = "*")
@RequestMapping("/datosHidrologica")

@RestController
public class DatosEstacionHidrologicaController {

    @Autowired
    private DatosEstacionHidrologicaRepository datosEstacionRepository;

    @PostMapping("/addDatosHidrologica")
    public ResponseEntity<String> guardarDatosHidrologica(@RequestBody DatosEstacionHidrologica datosEstacion) {
        try {
            DatosEstacionHidrologica nuevodatosEstacion = datosEstacionRepository.save(datosEstacion);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("datos estacion guardada con ID: " + nuevodatosEstacion.getIdHidrologica());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la datos estacion: " + e.getMessage());
        }
    }
}
