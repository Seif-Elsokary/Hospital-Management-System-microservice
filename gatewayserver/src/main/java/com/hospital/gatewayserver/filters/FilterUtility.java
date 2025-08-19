package com.hospital.gatewayserver.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.HttpHeaders;
import java.util.List;

@Component
public class FilterUtility {

    // Constant for the correlation ID header key
    public static final String CORRELATION_ID = "eazybank-correlation-id";

    /**
     * Get the correlation ID from request headers.
     * @param requestHeaders - incoming HTTP headers
     * @return correlation ID if present, otherwise null
     */
    public String getCorrelationId(HttpHeaders requestHeaders) {
        if (requestHeaders.get(CORRELATION_ID) != null) {
            // Get the list of values for the correlation header
            List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID);
            // Return the first value in the list
            return requestHeaderList.stream().findFirst().get();
        } else {
            return null; // Correlation ID not found
        }
    }

    /**
     * Add or update a header in the ServerWebExchange.
     * @param exchange - current request/response context
     * @param name - header name
     * @param value - header value
     * @return updated ServerWebExchange
     */
    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        // Use mutate() to create a copy of the request with a new header
        return exchange.mutate()
                .request(exchange.getRequest().mutate().header(name, value).build())
                .build();
    }

    /**
     * Convenience method to set the correlation ID in the request headers.
     * @param exchange - current request/response context
     * @param correlationId - correlation ID to set
     * @return updated ServerWebExchange
     */
    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

}
