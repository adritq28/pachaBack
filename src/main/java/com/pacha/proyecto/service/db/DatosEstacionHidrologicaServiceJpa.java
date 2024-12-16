package com.pacha.proyecto.service.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.pacha.proyecto.model.DatosEstacionHidrologica;
import com.pacha.proyecto.repository.DatosEstacionHidrologicaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Primary
public class DatosEstacionHidrologicaServiceJpa {

    @Autowired
    private DatosEstacionHidrologicaRepository datosEstacionRepo;

    public void editarHidrologica(DatosEstacionHidrologica request) {
        Optional<DatosEstacionHidrologica> existingRecord = datosEstacionRepo
                .findByIdHidrologica(request.getIdHidrologica());

        if (existingRecord.isPresent()) {
            DatosEstacionHidrologica Hidrologica = existingRecord.get();
            Hidrologica.setLimnimetro(request.getLimnimetro());
            Hidrologica.setFechaReg(request.getFechaReg());
            Hidrologica.setEdit(true);
            Hidrologica.setDelete(false);

            datosEstacionRepo.save(Hidrologica);
        } else {
            throw new EntityNotFoundException("Registro no encontrado con el ID: " + request.getIdHidrologica());
        }
    }

    public boolean eliminarDatosHidrologicaEstacion(int idHidrologica) {
        Optional<DatosEstacionHidrologica> datosEstacionOptional = datosEstacionRepo.findByIdHidrologica(idHidrologica);
        if (datosEstacionOptional.isPresent()) {
            DatosEstacionHidrologica datosEstacion = datosEstacionOptional.get();
            datosEstacion.setDelete(true);
            datosEstacionRepo.save(datosEstacion);
            return true;
        } else {
            return false;
        }
    }

}
