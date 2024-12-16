package com.pacha.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pacha.proyecto.model.Observador;
import com.pacha.proyecto.repository.ObservadorRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/observador")
public class ObservadorController {

    @Autowired
    private ObservadorRepository observadorRepo;

    @PostMapping("/addObservador")
    public ResponseEntity<String> guardarObservador(@RequestBody Observador observador) {
        try {
            Observador nuevoObservador = observadorRepo.save(observador);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Observador guardado con ID: " + nuevoObservador.getIdObservador());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el observador: " + e.getMessage());
        }
    }

}
