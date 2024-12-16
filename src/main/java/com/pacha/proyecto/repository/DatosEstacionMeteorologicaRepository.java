package com.pacha.proyecto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pacha.proyecto.model.DatosEstacionMeteorologica;

public interface DatosEstacionMeteorologicaRepository extends JpaRepository<DatosEstacionMeteorologica, Integer> {

        Optional<DatosEstacionMeteorologica> findByIdDatosEst(Integer id);

}
