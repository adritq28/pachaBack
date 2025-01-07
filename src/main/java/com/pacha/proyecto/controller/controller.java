package com.pacha.proyecto.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pacha.proyecto.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class controller {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/verusuarios")
    public String obtenerUsuariosConEstacion(Model model) {
        List<Object[]> listaUsuariosConEstacion = usuarioRepository.buscarUsuariosConEstacion();
        System.out.println("Datos obtenidos del repositorio: " + listaUsuariosConEstacion.size());
        model.addAttribute("testMessage", "Esto es una prueba");

        List<Map<String, Object>> usuariosConEstacion = new ArrayList<>();
        for (Object[] usuarioConEstacion : listaUsuariosConEstacion) {
            System.out.println("Usuario: " + Arrays.toString(usuarioConEstacion));
            Map<String, Object> usuario = new HashMap<>();
            usuario.put("idUsuario", usuarioConEstacion[0]);
            usuario.put("nombreMunicipio", usuarioConEstacion[1]);
            usuario.put("nombreEstacion", usuarioConEstacion[2]);
            usuario.put("tipoEstacion", usuarioConEstacion[3]);
            usuario.put("nombreCompleto", usuarioConEstacion[4]);
            usuario.put("telefono", usuarioConEstacion[5]);
            usuario.put("idEstacion", usuarioConEstacion[6]);
            usuario.put("codTipoEstacion", usuarioConEstacion[7]);
            usuario.put("imagen", usuarioConEstacion[8]);
            usuariosConEstacion.add(usuario);
        }
        model.addAttribute("usuarios", usuariosConEstacion);
        return "usuarios";
    }

}
