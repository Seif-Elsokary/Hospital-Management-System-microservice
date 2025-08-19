package com.hospital.gatewayserver.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseTraceFilter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);

    @Autowired
    FilterUtility filterUtility; // Utility for correlation ID handling

    @Bean
    public GlobalFilter postGlobalFilter() {
        // Define a global filter to run AFTER request is processed
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // Extract the request headers
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                // Get the correlation ID from the request
                String correlationId = filterUtility.getCorrelationId(requestHeaders);
                logger.debug("Updated the correlation id to the outbound headers: {}", correlationId);

                // Add the correlation ID to the response headers for tracing
                exchange.getResponse().getHeaders().add(filterUtility.CORRELATION_ID, correlationId);
            }));
        };
    }
}
