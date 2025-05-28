package com.spring.ai.tutorial.advisor.memory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author yingzi
 * @date 2025/5/28 08:31
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SqliteMemoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqliteMemoryApplication.class, args);
    }
}
