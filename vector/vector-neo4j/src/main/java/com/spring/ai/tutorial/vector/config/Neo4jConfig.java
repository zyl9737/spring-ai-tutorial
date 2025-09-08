package com.spring.ai.tutorial.vector.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.vectorstore.neo4j.Neo4jVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yingzi
 * @since 2025/9/8
 */
@Configuration
public class Neo4jConfig {

    private static final Logger logger = LoggerFactory.getLogger(Neo4jConfig.class);


    @Value("${spring.neo4j.uri}")
    private String uri;
    @Value("${spring.neo4j.authentication.username}")
    private String username;
    @Value("${spring.neo4j.authentication.password}")
    private String password;

    @Value("${spring.ai.vectorstore.neo4j.database-name}")
    private String databaseName;
    @Value("${spring.ai.vectorstore.neo4j.distance-type}")
    private Neo4jVectorStore.Neo4jDistanceType distanceType;
    @Value("${spring.ai.vectorstore.neo4j.index-name}")
    private String indexName;
    @Value("${spring.ai.vectorstore.neo4j.initialize-schema}")
    private boolean initializeSchema;
    @Value("${spring.ai.vectorstore.neo4j.embedding-dimension}")
    private int embeddingDimension;


    @Bean
    public Driver driver() {
        return GraphDatabase.driver(uri,
                AuthTokens.basic(username, password));
    }

    @Bean(name = "neo4jVectorStore")
    public Neo4jVectorStore vectorStore(Driver driver, EmbeddingModel embeddingModel) {
        logger.info("create neo4j vector store");

        return Neo4jVectorStore.builder(driver, embeddingModel)
                .databaseName(databaseName)                // Optional: defaults to "neo4j"
                .distanceType(distanceType) // Optional: defaults to COSINE
                .embeddingDimension(embeddingDimension)                      // Optional: defaults to 1536
                .label("Document")                     // Optional: defaults to "Document"
                .embeddingProperty("embedding")        // Optional: defaults to "embedding"
                .indexName(indexName)             // Optional: defaults to "spring-ai-document-index"
                .initializeSchema(initializeSchema)                // Optional: defaults to false
                .batchingStrategy(new TokenCountBatchingStrategy()) // Optional: defaults to TokenCountBatchingStrategy
                .build();
    }
}
