package com.scania.warranty.web;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/ui-schemas")
public class UiSchemaController {

    @GetMapping(value = "/{screenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUiSchema(@PathVariable String screenId) {
        String resourcePath = "ui-schemas/" + screenId + ".json";
        ClassPathResource resource = new ClassPathResource(resourcePath);

        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"UI schema not found for screenId: " + screenId + "\"}");
        }

        try {
            String json = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            return ResponseEntity.ok(json);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Failed to load UI schema: " + e.getMessage() + "\"}");
        }
    }
}

