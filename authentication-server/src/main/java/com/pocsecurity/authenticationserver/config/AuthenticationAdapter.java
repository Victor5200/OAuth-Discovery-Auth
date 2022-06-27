package com.pocsecurity.authenticationserver.config;

import com.pocsecurity.authenticationserver.model.Usuario;
import com.pocsecurity.authenticationserver.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Configuration

public class AuthenticationAdapter  extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void init(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String password = authentication.getCredentials().toString();
                Usuario usuario = usuarioService.retrieveUsuarioByName(authentication.getName());

                if (usuario != null && usuario.getName().compareTo(password) == 0) {
                    List<GrantedAuthority> grantedAuths = new ArrayList<>();
                    grantedAuths.add(new SimpleGrantedAuthority(usuario.getRole()));
                    return new UsernamePasswordAuthenticationToken(usuario, password, grantedAuths);
                } else {
                    throw new BadCredentialsException("invalid username or password");
                }
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.equals(UsernamePasswordAuthenticationToken.class);
            }
        });
    }
}
