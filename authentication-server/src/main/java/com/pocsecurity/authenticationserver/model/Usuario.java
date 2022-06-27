package com.pocsecurity.authenticationserver.model;

import lombok.Builder;
import lombok.Data;

import java.security.Principal;

@Data
@Builder
public class Usuario implements Principal {

    private int id;
    private String usuario;
    private String senha;
    private String role;

    @Override
    public String getName() {
        return null;
    }
}

