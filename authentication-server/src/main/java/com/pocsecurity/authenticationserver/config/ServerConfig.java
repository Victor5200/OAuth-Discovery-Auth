package com.pocsecurity.authenticationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;
import java.util.Collections;

@EnableAuthorizationServer
@Configuration
public class ServerConfig extends AuthorizationServerConfigurerAdapter {

    public static final String CLIENT_ID_MOCK = "clientIdMock";

    @Value("${oauth2.jwt-secret-key}")
    private String jwtSecretKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientId -> {
            if (!clientId.equals(CLIENT_ID_MOCK)) {
                return null;
            }

            final BaseClientDetails clientDetails = new BaseClientDetails();
            clientDetails.setClientId(CLIENT_ID_MOCK);
            clientDetails.setClientSecret("clientSecretMock");
            clientDetails.setAuthorizedGrantTypes(Arrays.asList("authorization_code", "refresh_token"));
            clientDetails.setScope(Collections.singleton("auth"));
            clientDetails.setAutoApproveScopes(Collections.singleton("auth"));
            return clientDetails;
        });
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.passwordEncoder(NoOpPasswordEncoder.getInstance()).tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final UserAuthenticationConverter authenticationConverter = new DefaultUserAuthenticationConverter();
        final DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        defaultAccessTokenConverter.setUserTokenConverter(authenticationConverter);
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtSecretKey);
        return converter;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer serverEndpointsConfigurer) {
        serverEndpointsConfigurer.authenticationManager(authenticationManager).accessTokenConverter(accessTokenConverter());
    }
}
