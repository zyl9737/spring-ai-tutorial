package com.spring.ai.tutorial.toolcall;

import com.spring.ai.tutorial.toolcall.component.weather.WeatherProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author yingzi
 * @date 2025/5/21 10:58
 */
@EnableConfigurationProperties(WeatherProperties.class)
@SpringBootApplication
public class ToolcallApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToolcallApplication.class, args);
    }
}
