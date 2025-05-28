package com.spring.ai.tutorial.advisor.memory.config;

import com.alibaba.cloud.ai.memory.jdbc.SQLiteChatMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author yingzi
 * @date 2025/5/28 08:32
 */
@Configuration
public class SqliteMemoryConfig {

    @Bean
    public SQLiteChatMemoryRepository sqliteChatMemoryRepository() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:advisor/advisor-memory-sqlite/src/main/resources/chat-memory.db");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return SQLiteChatMemoryRepository.sqliteBuilder()
                .jdbcTemplate(jdbcTemplate)
                .build();
    }
}
