package com.sustech.springboot;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import jakarta.annotation.PostConstruct;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@MapperScan("com.sustech.springboot.mapper")
@EnableMPP
public class SpringbootApplication {
    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {

        SpringApplication.run(SpringbootApplication.class, args);
    }

    @PostConstruct
    public void testDatabaseConnection() {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "SELECT 1";
            jdbcTemplate.queryForObject(sql, Integer.class);
            System.out.println("Database connection is successful.");
        } catch (Exception e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }

}
