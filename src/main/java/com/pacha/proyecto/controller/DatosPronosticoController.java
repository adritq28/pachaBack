package com.pacha.proyecto.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pacha.proyecto.model.DatosPronostico;
import com.pacha.proyecto.repository.DatosPronosticoRepository;
import com.pacha.proyecto.service.db.DatosPronosticoServiceJpa;

@CrossOrigin(origins = "*")
@RequestMapping("/datos_pronostico")

@RestController
public class DatosPronosticoController {

    @Autowired
    private DatosPronosticoRepository datosPronosticoRepository;
    @Autowired
    private DatosPronosticoServiceJpa pronosticoService;

    @PostMapping("/addDatosPronostico")
    public ResponseEntity<String> guanrdarDatosPronostico(@RequestBody DatosPronostico datosPronostico) {
        try {
            DatosPronostico nuevodatosPronostico = datosPronosticoRepository.save(datosPronostico);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("datos estacion guardada con ID: " + nuevodatosPronostico.getIdPronostico());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la datos estacion: " + e.getMessage());
        }
    }

    @GetMapping("/registro/{idCultivo}")
    public List<Map<String, Object>> pronosticoCultivo(@PathVariable int idCultivo) {
        List<Map<String, Object>> estacion = new ArrayList<>();
        List<Object[]> listaPronostico = datosPronosticoRepository.pronosticoCultivo(idCultivo);

        for (Object[] usuarioConEstacion : listaPronostico) {
            Map<String, Object> usuario = new HashMap<>();
            usuario.put("tempMax", usuarioConEstacion[0]);
            usuario.put("tempMin", usuarioConEstacion[1]);
            usuario.put("pcpn", usuarioConEstacion[2]);
            usuario.put("fecha", usuarioConEstacion[3]);
            usuario.put("fechaRangoDecenal", usuarioConEstacion[4]);
            estacion.add(usuario);
        }
        return estacion;
    }

    @GetMapping("/lista_zonas")
    public List<Map<String, Object>> listaZonas() {
        List<Map<String, Object>> estacionHidrologica = new ArrayList<>();
        List<Object[]> listaZona = datosPronosticoRepository.listaZonas();
        for (Object[] usuarioConEstacion : listaZona) {
            Map<String, Object> usuario = new HashMap<>();
            usuario.put("idZona", usuarioConEstacion[0]);
            usuario.put("nombreMunicipio", usuarioConEstacion[1]);
            usuario.put("nombreZona", usuarioConEstacion[2]);
            estacionHidrologica.add(usuario);
        }
        return estacionHidrologica;
    }

    @GetMapping("/lista_datos_zona/{idCultivo}")
    public List<Map<String, Object>> listaDatosMeteorologica(@PathVariable int idCultivo) {
        List<Map<String, Object>> estacionMeteorologica = new ArrayList<>();
        List<Object[]> listaZona = datosPronosticoRepository.listaDatosZona(idCultivo);
        for (Object[] datos : listaZona) {
            Boolean delete = (Boolean) datos[5];
            if (delete == null || !delete) {
                Map<String, Object> registro = new HashMap<>();
                registro.put("tempMax", datos[0]);
                registro.put("tempMin", datos[1]);
                registro.put("pcpn", datos[2]);
                registro.put("fecha", datos[3]);
                registro.put("idPronostico", datos[4]);
                registro.put("delete", delete);
                registro.put("fechaRangoDecenal", datos[6]);
                estacionMeteorologica.add(registro);
            }
        }

        return estacionMeteorologica;
    }

    @PostMapping("/editar")
    public ResponseEntity<?> editarPronostico(@RequestBody DatosPronostico request) {
        System.out.println("Request recibido: " + request);
        if (request.getIdPronostico() == null) {
            return ResponseEntity.badRequest().body("ID es requerido para actualizar los datos");
        }
        try {
            pronosticoService.editarPronostico(request);
            return ResponseEntity.ok().body("Datos actualizados correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar los datos: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{idPronostico}")
    public ResponseEntity<String> eliminarPronostico(@PathVariable int idPronostico) {
        boolean eliminado = pronosticoService.eliminarPronostico(idPronostico);
        if (eliminado) {
            return ResponseEntity.ok("Datos meteorológicos eliminados correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo eliminar los datos meteorológicos");
        }
    }

    @GetMapping("/contarDatosHoy/{idZona}")
    public ResponseEntity<?> contarDatosHoy(@PathVariable int idZona) {
        long count = pronosticoService.contarDatosHoy(idZona);
        return ResponseEntity.ok(Collections.singletonMap("count", count));
    }

}
