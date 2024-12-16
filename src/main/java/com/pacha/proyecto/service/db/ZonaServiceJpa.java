package com.pacha.proyecto.service.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.pacha.proyecto.model.Zona;
import com.pacha.proyecto.repository.ZonaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Primary
public class ZonaServiceJpa {

    @Autowired
    private ZonaRepository zonaRepo;

    public void editarZona(Zona request) {
        Optional<Zona> existingRecord = zonaRepo.findByIdZona(request.getIdZona());

        if (existingRecord.isPresent()) {
            Zona estacion = existingRecord.get();
            estacion.setNombre(request.getNombre());
            estacion.setIdMunicipio(request.getIdMunicipio());
            zonaRepo.save(estacion);
        } else {
            throw new EntityNotFoundException("Registro no encontrado con el ID: " + request.getIdZona());
        }
    }

}
