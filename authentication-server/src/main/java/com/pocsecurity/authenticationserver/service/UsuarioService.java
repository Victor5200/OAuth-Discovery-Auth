package com.pocsecurity.authenticationserver.service;

import com.pocsecurity.authenticationserver.model.Usuario;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Service
public class UsuarioService  {

    private List<Usuario> Usuarios = new ArrayList<>();

    @PostConstruct
    public void init() {
        Usuarios.add(Usuario.builder().id(1).usuario("admin").senha("admin").role("ADMIN").build());
        Usuarios.add(Usuario.builder().id(1).usuario("usuario").senha("teste").role("usuario").build());
    }

    public Usuario retrieveUsuarioByName(String Usuarioname) {
        return Usuarios.stream().filter(Usuario -> Usuario.getName().equals(Usuarioname)).findFirst().orElse(null);
    }
}
