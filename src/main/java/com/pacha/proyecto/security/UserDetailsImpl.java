package com.pacha.proyecto.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pacha.proyecto.model.Usuario;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getNombreUsuario();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    public String getNombre() {
        return usuario.getNombre();
    }

    public int getIdUsuario() {
        return usuario.getIdUsuario();
    }

    public String getApePat() {
        return usuario.getApePat();
    }

    public String getApeMat() {
        return usuario.getApeMat();
    }

    public String getCi() {
        return usuario.getCi();
    }

    public String getTelefono() {
        return usuario.getTelefono();
    }

    public String getImagen() {
        return usuario.getImagen();
    }

}