package com.pacha.proyecto.service.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.pacha.proyecto.model.Estacion;
import com.pacha.proyecto.repository.EstacionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Primary
public class EstacionServiceJpa {

    @Autowired
    private EstacionRepository estacionRepo;

    public void editarEstacion(Estacion request) {
        Optional<Estacion> existingRecord = estacionRepo.findByIdEstacion(request.getIdEstacion());

        if (existingRecord.isPresent()) {
            Estacion estacion = existingRecord.get();
            estacion.setNombre(request.getNombre());
            estacion.setIdMunicipio(request.getIdMunicipio());
            estacion.setTipoEstacion(request.getTipoEstacion());
            estacionRepo.save(estacion);
        } else {
            throw new EntityNotFoundException("Registro no encontrado con el ID: " + request.getIdEstacion());
        }
    }

}
