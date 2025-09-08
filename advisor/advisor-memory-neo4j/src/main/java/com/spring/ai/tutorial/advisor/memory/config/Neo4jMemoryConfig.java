package com.spring.ai.tutorial.advisor.memory.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.ai.chat.memory.repository.neo4j.Neo4jChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.neo4j.Neo4jChatMemoryRepositoryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yingzi
 * @since 2025/9/8
 */
@Configuration
public class Neo4jMemoryConfig {

    @Value("${spring.neo4j.uri}")
    private String uri;
    @Value("${spring.neo4j.authentication.username}")
    private String username;
    @Value("${spring.neo4j.authentication.password}")
    private String password;

    @Bean
    public Driver driver() {
        return GraphDatabase.driver(uri,
                AuthTokens.basic(username, password));
    }

    @Bean
    public Neo4jChatMemoryRepository neo4jChatMemoryRepository(Driver driver) {
        Neo4jChatMemoryRepositoryConfig config = Neo4jChatMemoryRepositoryConfig.builder()
                .withDriver(driver)
                .build();

        return new Neo4jChatMemoryRepository(config);
    }
}
