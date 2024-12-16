package com.pacha.proyecto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pacha.proyecto.model.DatosEstacionHidrologica;

public interface DatosEstacionHidrologicaRepository extends JpaRepository<DatosEstacionHidrologica, Integer> {

        Optional<DatosEstacionHidrologica> findByIdHidrologica(int id);

}
