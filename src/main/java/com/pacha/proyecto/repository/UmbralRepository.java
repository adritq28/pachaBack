package com.pacha.proyecto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pacha.proyecto.model.Umbrales;

public interface UmbralRepository extends JpaRepository<Umbrales, Integer> {

        Optional<Umbrales> findByIdFenologia(int integer);

}
