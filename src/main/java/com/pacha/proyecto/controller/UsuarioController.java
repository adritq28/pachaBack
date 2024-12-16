package com.pacha.proyecto.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pacha.proyecto.model.Usuario;
import com.pacha.proyecto.repository.UsuarioRepository;
import com.pacha.proyecto.service.db.UsuarioServiceJpa;

@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
@RestController
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioServiceJpa usuarioService;

    @GetMapping(value = "/verusuarios", produces = "application/json;charset=UTF-8")
    public List<Map<String, Object>> obtenerUsuariosConEstacion() {
        List<Map<String, Object>> usuariosConEstacion = new ArrayList<>();
        List<Object[]> listaUsuariosConEstacion = usuarioRepository.buscarUsuariosConEstacion();
        for (Object[] usuarioConEstacion : listaUsuariosConEstacion) {
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
        return usuariosConEstacion;
    }

    @GetMapping("/password/{idUsuario}")
    public String obtenerPassword(@PathVariable int idUsuario) {
        return usuarioService.obtenerPasswordIdUsuario(idUsuario);
    }

    @GetMapping("/perfil/{idUsuario}")
    public ResponseEntity<List<Map<String, Object>>> getPerfil(@PathVariable int idUsuario) {
        List<Object[]> perfilData = usuarioRepository.perfilObservador(idUsuario);

        List<Map<String, Object>> resultado = perfilData.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("nombreCompleto", row[0]);
            map.put("telefono", row[1]);
            map.put("imagen", row[2]);
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }

    @GetMapping(value = "/lista_usuario", produces = "application/json;charset=UTF-8")
    public List<Map<String, Object>> listaEstacionMteorologica() {
        List<Map<String, Object>> estacionHidrologica = new ArrayList<>();
        List<Object[]> listaestacionHidrologica = usuarioRepository.listaUsuario();
        for (Object[] usuarioConEstacion : listaestacionHidrologica) {
            // System.out.println("adawdansdasodaddasda");
            Map<String, Object> usuario = new HashMap<>();
            usuario.put("idUsuario", usuarioConEstacion[0]);
            usuario.put("nombre", usuarioConEstacion[1]);
            usuario.put("apePat", usuarioConEstacion[2]);
            usuario.put("apeMat", usuarioConEstacion[3]);
            usuario.put("ci", usuarioConEstacion[4]);
            usuario.put("admin", usuarioConEstacion[5]);
            usuario.put("telefono", usuarioConEstacion[6]);
            usuario.put("imagen", usuarioConEstacion[7]);
            usuario.put("rol", usuarioConEstacion[8]);
            usuario.put("estado", usuarioConEstacion[9]);
            usuario.put("password", usuarioConEstacion[10]);
            estacionHidrologica.add(usuario);
        }
        return estacionHidrologica;
    }

    @PostMapping("/editar")
    public ResponseEntity<?> editarUsuario(@RequestBody Usuario request) {
        if (request.getIdUsuario() == null) {
            return ResponseEntity.badRequest().body("ID es requerido para actualizar los datos");
        }
        usuarioService.editarUsuario(request);
        return ResponseEntity.ok().body("Datos actualizados correctamente");
    }

    @GetMapping("/roles/{idUsuario}")
    public ResponseEntity<?> obtenerDatosUsuario(@PathVariable Long idUsuario) {

        String rol = usuarioService.obtenerRolPorIdUsuario(idUsuario);
        List<Map<String, Object>> resultado = new ArrayList<>();

        switch (rol) {
            case "1":
                List<Object[]> datosAdmin = usuarioRepository.obtenerDatosAdministrador(idUsuario);
                resultado = mapearDatosAdmin(datosAdmin);
                break;
            case "2":
                List<Object[]> datosObservador = usuarioRepository.obtenerDatosObservador(idUsuario);
                resultado = mapearDatosObservador(datosObservador);
                break;
            case "3":
                List<Object[]> datosPromotor = usuarioRepository.obtenerDatosPromotor(idUsuario);
                resultado = mapearDatosPromotor(datosPromotor);
                break;
            default:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Rol no reconocido");
        }

        return ResponseEntity.ok(resultado);
    }

    private List<Map<String, Object>> mapearDatosAdmin(List<Object[]> datos) {
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Object[] fila : datos) {
            Map<String, Object> map = new HashMap<>();
            map.put("idUsuario", fila[0]);
            map.put("nombre", fila[1]);
            map.put("apePat", fila[2]);
            map.put("apeMat", fila[3]);
            map.put("ci", fila[4]);
            map.put("telefono", fila[5]);
            map.put("usuarioImagen", fila[6]);
            map.put("rol", fila[7]);
            map.put("password", fila[8]);
            resultado.add(map);
        }
        return resultado;
    }

    private List<Map<String, Object>> mapearDatosObservador(List<Object[]> datos) {
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Object[] fila : datos) {
            Map<String, Object> map = new HashMap<>();
            map.put("municipio", fila[0]);
            map.put("estacion", fila[1]);
            map.put("idUsuario", fila[2]);
            map.put("nombre", fila[3]);
            map.put("apePat", fila[4]);
            map.put("apeMat", fila[5]);
            map.put("ci", fila[6]);
            map.put("admin", fila[7]);
            map.put("telefono", fila[8]);
            map.put("usuarioImagen", fila[9]);
            map.put("rol", fila[10]);
            map.put("tipoEstacion", fila[11]);
            map.put("idEstacion", fila[12]);
            map.put("idMunicipio", fila[13]);
            resultado.add(map);
        }
        return resultado;
    }

    private List<Map<String, Object>> mapearDatosPromotor(List<Object[]> datos) {
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Object[] fila : datos) {
            Map<String, Object> map = new HashMap<>();
            map.put("municipio", fila[0]);
            map.put("zona", fila[1]);
            map.put("cultivoNombre", fila[2]);
            map.put("idUsuario", fila[3]);
            map.put("nombre", fila[4]);
            map.put("apePat", fila[5]);
            map.put("apeMat", fila[6]);
            map.put("ci", fila[7]);
            map.put("admin", fila[8]);
            map.put("telefono", fila[9]);
            map.put("usuarioImagen", fila[10]);
            map.put("cultivoImagen", fila[11]);
            map.put("rol", fila[12]);
            map.put("idZona", fila[13]);
            map.put("latitud", fila[14]);
            map.put("longitd", fila[15]);
            map.put("idMunicipio", fila[16]);
            map.put("tipoCultivo", fila[17]);
            map.put("fechaSiembra", fila[18]);
            map.put("idCultivo", fila[19]);
            map.put("edit", fila[20]);
            map.put("delete", fila[21]);
            resultado.add(map);
        }
        return resultado;
    }

    @DeleteMapping("/eliminar/{idUsuario}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable int idUsuario) {
        boolean eliminado = usuarioService.eliminarUsuario(idUsuario);
        if (eliminado) {
            return ResponseEntity.ok("Datos meteorológicos eliminados correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo eliminar los datos meteorológicos");
        }
    }

    @PutMapping("/actualizarUltimoAcceso/{idUsuario}")
    public ResponseEntity<?> actualizarUltimoAcceso(@PathVariable Integer idUsuario) {
        Usuario usuario = usuarioService.buscarPorIdUsuario(idUsuario);
        if (usuario != null) {
            usuario.setUltimoAcceso(new Date());
            usuarioRepository.save(usuario);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PostMapping("/addUsuario")
    public ResponseEntity<Map<String, Object>> addUsuario(
            @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("nombre") String nombre,
            @RequestParam("apePat") String apePat,
            @RequestParam("apeMat") String apeMat,
            @RequestParam("telefono") String telefono,
            @RequestParam("ci") String ci,
            @RequestParam("password") String password,
            @RequestParam("fechaCreacion") String fechaCreacion,
            @RequestParam("ultimoAcceso") String ultimoAcceso,
            @RequestParam("estado") Boolean estado,
            @RequestParam("rol") String rol,
            @RequestParam("delete") Boolean delete,
            @RequestParam("edit") Boolean edit,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @RequestParam("correoElectronico") String correoElectronico) {

        try {
            String nombreImagen = null;
            if (imagen != null && !imagen.isEmpty()) {
                nombreImagen = UUID.randomUUID().toString() + ".png";
                String rutaImagen = "D:/ProyectoHelvetas/Frontend/HelvetasFrontv8/front/helvetasfront/images/"
                        + nombreImagen;

                File directorio = new File("D:/ProyectoHelvetas/Frontend/HelvetasFrontv8/front/helvetasfront/images/");
                if (!directorio.exists()) {
                    directorio.mkdirs();
                }

                File archivoImagen = new File(rutaImagen);
                imagen.transferTo(archivoImagen);

                System.out.println("Imagen guardada: " + nombreImagen);
            }

            Usuario nuevoUsuario = new Usuario();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            nuevoUsuario.setNombreUsuario(nombreUsuario);
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setApePat(apePat);
            nuevoUsuario.setApeMat(apeMat);
            nuevoUsuario.setTelefono(telefono);
            nuevoUsuario.setCi(ci);
            nuevoUsuario.setPassword(passwordEncoder.encode(password));
            nuevoUsuario.setFechaCreacion(Timestamp.valueOf(LocalDateTime.parse(fechaCreacion)));
            nuevoUsuario.setUltimoAcceso(Timestamp.valueOf(LocalDateTime.parse(ultimoAcceso)));
            nuevoUsuario.setEstado(estado);
            nuevoUsuario.setRol(rol);
            nuevoUsuario.setDelete(delete);
            nuevoUsuario.setEdit(edit);
            nuevoUsuario.setImagen(nombreImagen);
            nuevoUsuario.setCorreoElectronico(correoElectronico);

            System.out.println("Usuario a guardar: " + nuevoUsuario);

            Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Usuario creado correctamente");
            response.put("idUsuario", usuarioGuardado.getIdUsuario());

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("message", "Error al guardar la imagen"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("message", "Error en el formato de fecha"),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("message", "Error al crear usuario"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            String rutaBase = "D:/ProyectoHelvetas/Frontend/HelvetasFrontv8/front/helvetasfront/images/";
            Path rutaImagen = Paths.get(rutaBase).resolve(filename).normalize();
            Resource resource = new UrlResource(rutaImagen.toUri());

            if (!resource.exists()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .cacheControl(CacheControl.noCache())
                    .body(resource);
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
