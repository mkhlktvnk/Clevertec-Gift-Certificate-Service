package ru.clevertec.ecl.domain.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@PropertySource("classpath:jdbc-postgres.properties")
public class DatabaseProperties {
    @Value("classpath:jdbc.url")
    private String url;

    @Value("classpath:jdbc.username")
    private String username;

    @Value("classpath:jdbc.password")
    private String password;

    @Value("classpath:jdbc.driver")
    private String driver;

    @Value("classpath:jdbc.connections")
    private Integer connections;
}
