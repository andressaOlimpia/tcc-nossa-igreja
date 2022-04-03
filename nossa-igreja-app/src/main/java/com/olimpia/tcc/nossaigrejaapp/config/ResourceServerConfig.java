package com.olimpia.tcc.nossaigrejaapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .cors()
                .and()
                .authorizeRequests().antMatchers("" +
                        "/users/cadastrar",
                        "/v2/api-docs", 
                        "/configuration/ui", 
                        "/swagger-resources/**", 
                        "/configuration/**",
                        "/swagger-ui.html", 
                        "/webjars/**", 
                        "/swagger-ui/**", 
                        "/v3/api-docs/**").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().access("#oauth2.hasScope('oauth2')");

    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
            }
        };
    }

}
