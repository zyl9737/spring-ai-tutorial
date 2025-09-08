package com.spring.ai.tutorial.advisor.memory.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.neo4j.Neo4jChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

/**
 * @author yingzi
 * @since 2025/9/8
 */
@RestController
@RequestMapping("/advisor/memory/neo4j")
public class Neo4jMemoryController {

    private final ChatClient chatClient;
    private final int MAX_MESSAGES = 100;
    private final MessageWindowChatMemory messageWindowChatMemory;

    public Neo4jMemoryController(ChatClient.Builder builder,  Neo4jChatMemoryRepository neo4jChatMemoryRepository) {
        this.messageWindowChatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(neo4jChatMemoryRepository)
                .maxMessages(MAX_MESSAGES)
                .build();

        this.chatClient = builder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(messageWindowChatMemory)
                                .build()
                )
                .build();
    }

    @GetMapping("/call")
    public String call(@RequestParam(value = "query", defaultValue = "你好，我的外号是影子，请记住呀") String query,
                       @RequestParam(value = "conversation_id", defaultValue = "yingzi") String conversationId
    ) {
        return chatClient.prompt(query)
                .advisors(
                        a -> a.param(CONVERSATION_ID, conversationId)
                )
                .call().content();
    }

    @GetMapping("/messages")
    public List<Message> messages(@RequestParam(value = "conversation_id", defaultValue = "yingzi") String conversationId) {
        return messageWindowChatMemory.get(conversationId);
    }
}
