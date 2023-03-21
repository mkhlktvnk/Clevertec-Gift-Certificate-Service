package ru.clevertec.ecl.domain.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ru.clevertec.ecl")
@RequiredArgsConstructor
public class DataSourceConfig {
    private final DatabaseProperties databaseProperties;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseProperties.getUrl());
        hikariConfig.setUsername(databaseProperties.getUsername());
        hikariConfig.setPassword(databaseProperties.getPassword());
        hikariConfig.setMaximumPoolSize(databaseProperties.getConnections());
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
