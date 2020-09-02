package com.github.pedrobacchini;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.MySQLContainer;

import java.util.HashMap;
import java.util.Map;

public class DatabaseLifecycle implements QuarkusTestResourceLifecycleManager {

    private static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql/mysql-server:8.0.19");

    @Override
    public Map<String, String> start() {
        MYSQL.start();
        Map<String, String> properties = new HashMap<>();
        properties.put("quarkus.datasource.jdbc.url", MYSQL.getJdbcUrl());
        properties.put("quarkus.datasource.username", MYSQL.getUsername());
        properties.put("quarkus.datasource.password", MYSQL.getPassword());
        return properties;
    }

    @Override
    public void stop() {
        MYSQL.stop();
    }
}
