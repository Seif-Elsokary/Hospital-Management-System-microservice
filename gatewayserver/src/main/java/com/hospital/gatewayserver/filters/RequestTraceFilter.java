package com.hospital.gatewayserver.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1) // Define filter execution order (1 = high priority)
@Component
public class RequestTraceFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    FilterUtility filterUtility; // Utility class for handling correlation ID

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

        if (isCorrelationIdPresent(requestHeaders)) {
            // If correlation ID already exists in the request headers
            logger.debug("eazyBank-correlation-id found in RequestTraceFilter : {}",
                    filterUtility.getCorrelationId(requestHeaders));
        } else {
            // Generate a new correlation ID if not present
            String correlationID = generateCorrelationId();
            exchange = filterUtility.setCorrelationId(exchange, correlationID);
            logger.debug("eazyBank-correlation-id generated in RequestTraceFilter : {}", correlationID);
        }

        // Continue processing the request through the filter chain
        return chain.filter(exchange);
    }

    /**
     * Check if the correlation ID header is already present
     */
    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        return filterUtility.getCorrelationId(requestHeaders) != null;
    }

    /**
     * Generate a unique correlation ID using UUID
     */
    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
}
