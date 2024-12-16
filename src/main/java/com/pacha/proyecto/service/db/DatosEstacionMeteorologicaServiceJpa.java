package com.pacha.proyecto.service.db;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.pacha.proyecto.model.DatosEstacionMeteorologica;
import com.pacha.proyecto.repository.DatosEstacionHidrologicaRepository;
import com.pacha.proyecto.repository.DatosEstacionMeteorologicaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Primary
public class DatosEstacionMeteorologicaServiceJpa {

    @Autowired
    private DatosEstacionMeteorologicaRepository datosEstacionRepo;

    @Autowired
    private DatosEstacionHidrologicaRepository datosHidroRepo;

    public void editarMeteorologica(DatosEstacionMeteorologica request) {
        Optional<DatosEstacionMeteorologica> existingRecord = datosEstacionRepo
                .findByIdDatosEst(request.getIdDatosEst());

        if (existingRecord.isPresent()) {
            DatosEstacionMeteorologica meteorologica = existingRecord.get();
            meteorologica.setTempMax(request.getTempMax());
            meteorologica.setTempMin(request.getTempMin());
            meteorologica.setPcpn(request.getPcpn());
            meteorologica.setTempAmb(request.getTempAmb());
            meteorologica.setDirViento(request.getDirViento());
            meteorologica.setVelViento(request.getVelViento());
            meteorologica.setTaevap(request.getTaevap());
            meteorologica.setFechaReg(request.getFechaReg());
            meteorologica.setEditar(true);
            meteorologica.setDelete(false);

            datosEstacionRepo.save(meteorologica);
        } else {
            throw new EntityNotFoundException("Registro no encontrado con el ID: " + request.getIdDatosEst());
        }
    }

    public boolean eliminarDatosEstacion(int idDatosEst) {
        Optional<DatosEstacionMeteorologica> datosEstacionOptional = datosEstacionRepo.findByIdDatosEst(idDatosEst);
        if (datosEstacionOptional.isPresent()) {
            DatosEstacionMeteorologica datosEstacion = datosEstacionOptional.get();
            datosEstacion.setDelete(true);
            datosEstacionRepo.save(datosEstacion);
            return true;
        } else {
            return false;
        }
    }
}
