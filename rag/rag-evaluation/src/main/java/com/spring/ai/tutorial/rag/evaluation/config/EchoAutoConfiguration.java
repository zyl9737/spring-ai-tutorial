package com.spring.ai.tutorial.rag.evaluation.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * @author yingzi
 * @since 2025/6/11
 */
@AutoConfiguration
public class EchoAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(EchoAutoConfiguration.class);


    public EchoAutoConfiguration() {
        logger.info("EchoAutoConfiguration");
    }
}
