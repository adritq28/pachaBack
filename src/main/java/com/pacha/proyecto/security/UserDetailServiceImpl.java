package com.pacha.proyecto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pacha.proyecto.model.Usuario;
import com.pacha.proyecto.repository.UsuarioRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findOneByNombreUsuario(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("El usuario con nombreUsuario " + username + "no existe"));

        return new UserDetailsImpl(usuario);
    }

}
