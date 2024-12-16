package com.pacha.proyecto.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pacha.proyecto.model.Municipio;
import com.pacha.proyecto.repository.MunicipioRepository;

@CrossOrigin(origins = "*")
@RequestMapping("/municipio")
@RestController
public class MunicipioController {

    @Autowired
    private MunicipioRepository municipioRepository;

    @GetMapping("/zona/{id}")
    public List<Map<String, Object>> buscaZonas(@PathVariable int id) {
        List<Map<String, Object>> municipioZona = new ArrayList<>();
        List<Object[]> listamunicipioZona = municipioRepository.obtenerZonasMunicipio(id);
        for (Object[] municipioConZona : listamunicipioZona) {
            Map<String, Object> municipio = new HashMap<>();
            municipio.put("idMunicipio", municipioConZona[0]);
            municipio.put("nombreMunicipio", municipioConZona[1]);
            municipio.put("idZona", municipioConZona[2]);
            municipio.put("nombreZona", municipioConZona[3]);
            municipio.put("nombreCultivo", municipioConZona[4]);
            municipio.put("idCultivo", municipioConZona[5]);
            municipio.put("latitud", municipioConZona[6]);
            municipio.put("longitud", municipioConZona[7]);
            municipio.put("nombreFechaSiembra", municipioConZona[8]);
            municipioZona.add(municipio);
        }
        return municipioZona;
    }

    @GetMapping("/lista_municipio")
    public List<Map<String, Object>> listaEstaciones() {
        List<Map<String, Object>> estaciones = new ArrayList<>();
        List<Object[]> listaestacionHidrologica = municipioRepository.listaMunicipio();
        for (Object[] usuarioConEstacion : listaestacionHidrologica) {
            Map<String, Object> usuario = new HashMap<>();
            usuario.put("nombreMunicipio", usuarioConEstacion[0]);
            usuario.put("idMunicipio", usuarioConEstacion[1]);
            estaciones.add(usuario);
        }
        return estaciones;
    }

    @PostMapping("/addMunicipio")
    public ResponseEntity<Map<String, Object>> addMunicipio(@RequestParam("nombre") String nombre,
            @RequestParam("fechaCreacion") String fechaCreacion,
            @RequestParam("delete") Boolean delete,
            @RequestParam("edit") Boolean edit,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {

        try {
            String nombreImagen = null;
            if (imagen != null && !imagen.isEmpty()) {
                nombreImagen = UUID.randomUUID().toString() + ".png";
                String rutaImagen = "D:/ProyectoHelvetas/Frontend/HelvetasFrontv8/helvetasfront/images/" + nombreImagen;

                File directorio = new File("D:/ProyectoHelvetas/Frontend/HelvetasFrontv8/helvetasfront/images");
                if (!directorio.exists()) {
                    directorio.mkdirs();
                }

                File archivoImagen = new File(rutaImagen);
                imagen.transferTo(archivoImagen);
            }

            Municipio nuevoUsuario = new Municipio();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setFechaCreacion(Timestamp.valueOf(LocalDateTime.parse(fechaCreacion)));
            nuevoUsuario.setDelete(delete);
            nuevoUsuario.setEdit(edit);
            nuevoUsuario.setImagen(nombreImagen);
            Municipio municipioGuardado = municipioRepository.save(nuevoUsuario);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Municipio creado correctamente");
            response.put("idMunicipio", municipioGuardado.getIdMunicipio());

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("message", "Error al guardar la imagen"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("message", "Error al crear Municipio"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
