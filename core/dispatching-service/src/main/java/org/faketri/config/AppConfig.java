package org.faketri.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class AppConfig {

    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public WebClient webClient(WebClient.Builder builder, @Value("${LOCATION_CLIENT_URL}") String baseUrl) {
        return builder
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().wiretap(true)
                ))
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return (request, next) -> {
            log.info("REQUEST: {} {}", request.method(), request.url());
            request.headers().forEach((name, values) ->
                    values.forEach(value -> log.info("{} : {}", name, value))
            );
            return next.exchange(request);
        };
    }

    private ExchangeFilterFunction logResponse() {
        return (request, next) ->
                next.exchange(request)
                        .doOnNext(response ->
                                log.info("RESPONSE: status {}", response.statusCode().value())
                        );
    }
}
