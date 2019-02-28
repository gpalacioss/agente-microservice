package com.legosoft.agentes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class MysqlConfig {

//    @Value("${jpa.mysql.driver}")
//    private String driver;
//
//    @Value("${jpa.mysql.url}")
//    private String url;
//
//    @Value("${jpa.mysql.username}")
//    private String username;
//
//    @Value("${jpa.mysql.password")
//    private String password;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.20.109:3600/agentes");
//        dataSource.setUrl("jdbc:mysql://localhost:3600/agentes");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");
        return dataSource;
    }

}
