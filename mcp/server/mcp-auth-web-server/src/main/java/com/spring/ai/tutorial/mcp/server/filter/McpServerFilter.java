package com.spring.ai.tutorial.mcp.server.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author yingzi
 * @since 2025/9/17
 */
@Component
public class McpServerFilter implements WebFilter {

    private static final String TOKEN_HEADER = "token-yingzi-1";
    private static final String TOKEN_VALUE = "yingzi-1";

    private static final Logger logger = LoggerFactory.getLogger(McpServerFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // 获取请求头中的token值
        String token = exchange.getRequest().getHeaders().getFirst(TOKEN_HEADER);

        // 检查token是否存在且值正确
        if (TOKEN_VALUE.equals(token)) {
            logger.info("preHandle: 请求的URL: {}", exchange.getRequest().getURI());
            logger.info("preHandle: 请求的TOKEN: {}", token);
            // token验证通过，继续处理请求
            return chain.filter(exchange);
        } else {
            // token验证失败，返回401未授权错误
            logger.warn("Token验证失败: 请求的URL: {}, 提供的TOKEN: {}", exchange.getRequest().getURI(), token);
            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
