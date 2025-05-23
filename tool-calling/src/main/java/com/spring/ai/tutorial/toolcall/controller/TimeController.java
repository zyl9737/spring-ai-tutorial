package com.spring.ai.tutorial.toolcall.controller;

import com.spring.ai.tutorial.toolcall.component.time.method.TimeTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author yingzi
 * @date 2025/5/21 11:01
 */

@RestController
@RequestMapping("/chat/time")
public class TimeController {

    private static final Logger logger = LoggerFactory.getLogger(TimeController.class);

    private final ChatClient chatClient;

    public TimeController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .build();
    }

    /**
     * 无工具版
     */
    @GetMapping("/call")
    public String call(@RequestParam(value = "query", defaultValue = "请告诉我现在北京时间几点了") String query) {
        return chatClient.prompt(query).call().content();
    }

    /**
     * 调用工具版 - function
     */
    @GetMapping("/call/tool-function")
    public String callToolFunction(@RequestParam(value = "query", defaultValue = "请告诉我现在北京时间几点了") String query) {
        return chatClient.prompt(query).toolNames("getCityTimeFunction").call().content();
    }

    /**
     * 调用工具版 - method
     */
    @GetMapping("/call/tool-method")
    public String callToolMethod(@RequestParam(value = "query", defaultValue = "请告诉我现在北京时间几点了") String query) {
        return chatClient.prompt(query).tools(new TimeTools()).call().content();
    }

    /**
     * stream 调用工具版 - method - false
     */
    @GetMapping("/call/tool-method-false")
    public String callToolMethodFalse(@RequestParam(value = "query", defaultValue = "请告诉我现在北京时间几点了") String query) {
        ChatClient.CallResponseSpec call = chatClient.prompt(query).tools(new TimeTools())
                .options(ToolCallingChatOptions.builder()
                        .internalToolExecutionEnabled(false)  // 禁用内部工具执行
                        .build()
                )
                .call();
        return call.content();
    }

    /**
     * stream 调用工具版 - method
     */
    @GetMapping("/stream/tool-method")
    public Flux<String> streamToolMethod(@RequestParam(value = "query", defaultValue = "请告诉我现在北京时间几点了") String query) {
        ChatClient.StreamResponseSpec stream = chatClient.prompt(query)
                .tools(new TimeTools())
                .stream();
        return stream.content();
    }

    /**
     * stream 调用工具版 - method - false
     */
    @GetMapping("/stream/tool-method-false")
    public Flux<String> streamToolMethodFalse(@RequestParam(value = "query", defaultValue = "请告诉我现在北京时间几点了") String query) {
        ChatClient.StreamResponseSpec stream = chatClient.prompt(query).tools(new TimeTools())
                .options(ToolCallingChatOptions.builder()
                        .internalToolExecutionEnabled(false)  // 禁用内部工具执行
                        .build()
                )
                .stream();
        return stream.content();
    }
}

