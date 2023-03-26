package ru.clevertec.ecl.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Value("${datasource.url}")
    private String datasourceUrl;

    @Value("${datasource.username}")
    private String datasourceUsername;

    @Value("${datasource.password}")
    private String datasourcePassword;

    @Value("${datasource.driver}")
    private String datasourceDriver;

    @Value("${datasource.connections}")
    private Integer datasourceConnections;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(datasourceUrl);
        hikariConfig.setUsername(datasourceUsername);
        hikariConfig.setPassword(datasourcePassword);
        hikariConfig.setDriverClassName(datasourceDriver);
        hikariConfig.setMaximumPoolSize(datasourceConnections);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
