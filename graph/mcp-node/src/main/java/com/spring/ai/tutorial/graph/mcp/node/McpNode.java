package com.spring.ai.tutorial.graph.mcp.node;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import com.spring.ai.tutorial.graph.mcp.controller.McpController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yingzi
 * @since 2025/6/13
 */

public class McpNode implements NodeAction {

    private static final Logger logger = LoggerFactory.getLogger(McpNode.class);


    private final ChatClient chatClient;

    public McpNode(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools) {
        ToolCallback[] toolCallbacks = tools.getToolCallbacks();
        for (ToolCallback toolCallback : toolCallbacks) {
            logger.info("Mcp Node load ToolCallback: " + toolCallback.getToolDefinition().name());
        }

        this.chatClient = chatClientBuilder
                .defaultToolCallbacks(toolCallbacks)
                .build();
    }


    @Override
    public Map<String, Object> apply(OverAllState state) throws Exception {
        String query = state.value("query", "");
        Flux<String> streamResult = chatClient.prompt(query).stream().content();
        String result = streamResult.reduce("", (acc, item) -> acc + item).block();

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("mcp_content", result);

        return resultMap;
    }
}
