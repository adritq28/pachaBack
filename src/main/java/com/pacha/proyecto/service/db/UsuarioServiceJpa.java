package com.pacha.proyecto.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pacha.proyecto.model.Usuario;
import com.pacha.proyecto.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Primary
public class UsuarioServiceJpa {

    @Autowired
    private UsuarioRepository usuarioRepo;

    public boolean changeUserPassword(String email, String newPassword) {
        Optional<Usuario> userOptional = usuarioRepo.findByCorreoElectronico(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (userOptional.isPresent()) {
            Usuario usuario = userOptional.get();
            usuario.setPassword(passwordEncoder.encode(newPassword));
            usuarioRepo.save(usuario);
            return true;
        }
        return false;
    }

    public String obtenerPasswordIdUsuario(int idUsuario) {
        return usuarioRepo.findPasswordByIdUsuario(idUsuario);
    }

    public Usuario buscarPorIdUsuario(int idUsuario) {
        Optional<Usuario> usuario = usuarioRepo.findById(idUsuario);
        return usuario.get();
    }

    public void editarUsuario(Usuario request) {
        Optional<Usuario> existingRecord = usuarioRepo.findByIdUsuario(request.getIdUsuario());

        if (existingRecord.isPresent()) {
            Usuario usuario = existingRecord.get();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            usuario.setImagen(request.getImagen());
            usuario.setNombre(request.getNombre());
            usuario.setApePat(request.getApePat());
            usuario.setApeMat(request.getApeMat());
            usuario.setCi(request.getCi());
            usuario.setAdmin(request.isAdmin());
            usuario.setTelefono(request.getTelefono());
            if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
                usuario.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            usuarioRepo.save(usuario);
        } else {
            throw new EntityNotFoundException("Registro no encontrado con el ID: " + request.getIdUsuario());
        }
    }

    public String obtenerRolPorIdUsuario(Long idUsuario) {
        List<Object[]> result = usuarioRepo.findRolByIdUsuario(idUsuario);
        if (!result.isEmpty()) {
            Object rol = result.get(0)[1];
            System.out.println("Valor del rol: " + rol);
            return rol.toString();
        }
        return null;
    }

    public boolean eliminarUsuario(int idUsuario) {
        Optional<Usuario> usuarioOptional = usuarioRepo.findByIdUsuario(idUsuario);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setDelete(true);
            usuarioRepo.save(usuario);
            return true;
        } else {
            return false;
        }
    }
}
