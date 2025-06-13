package com.spring.ai.tutorial.graph.human.config;

import com.alibaba.cloud.ai.graph.KeyStrategy;
import com.alibaba.cloud.ai.graph.KeyStrategyFactory;
import com.alibaba.cloud.ai.graph.StateGraph;
import com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author yingzi
 * @since 2025/6/13
 */
@Configuration
public class GraphHumanConfiguration {

    @Bean
    public StateGraph humanGraph() {
        KeyStrategyFactory keyStrategyFactory = () -> {
            HashMap<String, KeyStrategy> keyStrategyHashMap = new HashMap<>();
            // 用户输入
            keyStrategyHashMap.put("query", new ReplaceStrategy());
            keyStrategyHashMap.put("thread_id", new ReplaceStrategy());

            keyStrategyHashMap.put("expander_number", new ReplaceStrategy());
            keyStrategyHashMap.put("expander_content", new ReplaceStrategy());

            // 人类反馈
            keyStrategyHashMap.put("feed_back", new ReplaceStrategy());
            keyStrategyHashMap.put("feed_back_content", new ReplaceStrategy());
            return keyStrategyHashMap;
        };

        StateGraph stateGraph = new StateGraph(keyStrategyFactory);

        return stateGraph;
    }
}
