package com.brewed.core;

import com.brewed.api.FilterDescriptionRangeDeserilizer;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Range;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@SpringBootApplication
@RestController
public class Application {

    public static String EXPERT_ID = "Expert User";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    private Connector redirectConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(80);
        connector.setSecure(false);
        connector.setRedirectPort(443);
        return connector;
    }

    @CrossOrigin
    @RequestMapping("/api/user")
    public Principal user(Principal user) {
        return user;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer objectMapperBuilder() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.deserializerByType(Range.class,
                                                                                           new FilterDescriptionRangeDeserilizer());
    }

    @CrossOrigin
    @RequestMapping("/.well-known/acme-challenge/zXLLgB0cpG1NKmHSIY_kKOmWMDX1X2JYsrVkxRoeAb4")
    public ResponseEntity<String> download() throws IOException {
        InputStream in = getClass().getClassLoader()
                                   .getResourceAsStream("https/zXLLgB0cpG1NKmHSIY_kKOmWMDX1X2JYsrVkxRoeAb4");
        String result = IOUtils.toString(in, StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                             .contentLength(result.length())
                             .contentType(MediaType.APPLICATION_OCTET_STREAM)
                             .body(result);
    }


    @Configuration
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requiresChannel()
                .anyRequest()
                .requiresSecure()
                .and()
                .csrf()
                .disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(
                        //rest
                        //beer
                        "/.well-known/acme-challenge/zXLLgB0cpG1NKmHSIY_kKOmWMDX1X2JYsrVkxRoeAb4",
                        "/api/beer",
                        "/api/beer/v2",
                        "/api/beer/{id}",
                        "/api/beer/findByAttrs",
                        "/api/beer/import",
                        "/api/beer/import/combined",
                        //brewery
                        "/api/brewery/{id}",
                        "/api/brewery",
                        "/api/brewery/{name}",
                        //user
                        "/user",
                        //beer-characteristics
                        "/api/beer-ratings",
                        "/api/beer-ratings/all",
                        "/api/beer-ratings/compound",
                        //predict
                        "/api/predict",
                        //type and note
                        "/api/beer-note/{id}",
                        "/api/beer-type/{id}",
                        "/api/beer-type",
                        "/api/beer-note",
                        "/api/beer-type/all",
                        "/api/beer-note/all",
                        //web
                        "/",
                        "/privacy",
                        "/home", "/angular-input-masks-standalone.min.js",
                        "/favicon.ico", "/css/**", "/js/**", "/images/**", "/webjars/**",
                        "/**/favicon.ico ", "/runtime*.js", "/polyfills*.js", "/styles*.js", "/vendor*.js",
                        "/main-es2015.js", "/styles*.css", "/main*.js", "/scripts.js", "/assets/*", "/scripts.*.js"
                )
                .permitAll()
                .anyRequest()
                .authenticated();
        }


        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("spring")
                .password(encoder.encode("secret"))
                .roles("ADMIN");
        }
    }
}

