package com.example.demo.client;

import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "prefix-service", url = "http://localhost:8082/${prefix-service.version}")
public interface PrefixClient {

    @GetMapping("/prefix")
    ResponseEntity<Prefix> getPrefix();

    @Data
    class Prefix {
        private String prefix;
    }
}
