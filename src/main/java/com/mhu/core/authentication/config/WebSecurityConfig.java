package com.mhu.core.authentication.config;

import com.mhu.core.authentication.jwt.JwtFilter;
import com.mhu.core.authentication.utils.AuthEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    private final String passedURI = "/api/auth/**";
    private final String authorizeURI = "/api/service/**";

    @Autowired
    private JwtFilter jwtFilter;


    @Autowired
    @Qualifier("AuthEntryPoint")
    private AuthEntry authEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(passedURI)
                .permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers(authorizeURI)
                .authenticated().anyRequest().permitAll()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
