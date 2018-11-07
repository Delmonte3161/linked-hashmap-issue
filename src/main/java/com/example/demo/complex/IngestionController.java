package com.example.demo.complex;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/complex-demo")
@RequiredArgsConstructor
@Slf4j
public class IngestionController {
    private final IngestionEventModelRepository repo;

    public static IngestionEventModel map(@NonNull final IngestionEvent in) {
        log.debug("map(IngestionEvent) -> {}", in);

        IngestionEventModel out = new IngestionEventModel();

        out.setDomainLink(in.getDomainLink());
        out.setForeignId(in.getForeignId());

        out.setPayload(in.getPayload());

        out.setRelationships(in.getRelationships());
        out.setRetryCount(in.getRetryCount());
        out.setSourceId(in.getSourceId());
        out.setTargetDomain(in.getTargetDomain());
        out.setTenantId(in.getTenantId());
        out.setTimeToLive(in.getTimeToLive());
        out.setId(in.getIngestionId());
        if (in.getStatus() != null)
            out.setStatus(in.getStatus());
        if (in.getStatusReason() != null)
            out.setStatusReason(in.getStatusReason());

        return out;
    }

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public IngestionEventModel doStuff(@RequestBody String jsonData) throws IOException {
        log.info("jsonData in: {}", jsonData);

        ObjectMapper mapper = new ObjectMapper();
        IngestionEvent event = mapper.readValue(jsonData, IngestionEvent.class);

        log.info("IngestionEvent: {}", event);
        IngestionEventModel model = map(event);

        log.info("Broken when ObjectMapper uses LinkedHashMap implementation: {}", model);

        return repo.save(model);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public IngestionEventModel doStuff() throws IOException {
        IngestionEventModel model = new IngestionEventModel();
        model.setSourceId("ScoringProcessor:ONMS Provisioning");
        model.setTargetDomain("target-domain-score");
        model.setTenantId("mass");
        model.setForeignId("19957608-ee66-4389-b9b8-4dfc49ef53ec");

        Map<String, Object> relationships = new HashMap<>();
        relationships.put("deviceId", "19957608-ee66-4389-b9b8-4dfc49ef53ec");
        model.setRelationships(relationships);
        Map<String, Object> scorePayload = new HashMap<>();

        scorePayload.put("score", 1.0);
        scorePayload.put("scoreType", "ONMS Provisioning");

        // Add metric values list
        List<Map<String, Object>> metricValuesList = new ArrayList<>();

        Map<String, Object> metricValueMap = new HashMap<>();
        metricValueMap.put("metric", "DNS");
        metricValueMap.put("description", "Device is in DNS");
        metricValueMap.put("weight", 1.0);
        metricValueMap.put("value", 0.0);
        metricValuesList.add(metricValueMap);

        Map<String, Object> metricValueMap2 = new HashMap<>();
        metricValueMap2.put("metric", "Domain");
        metricValueMap2.put("description", "Device is in Domain");
        metricValueMap2.put("weight", 1.0);
        metricValueMap2.put("value", 1.0);
        metricValuesList.add(metricValueMap2);

        Map<String, Object> metricValueMap3 = new HashMap<>();
        metricValueMap3.put("metric", "Location");
        metricValueMap3.put("description", "Device location site is known");
        metricValueMap3.put("weight", 1.0);
        metricValueMap3.put("value", 0.0);
        metricValuesList.add(metricValueMap3);

        scorePayload.put("metrics", metricValuesList);
        model.setPayload(scorePayload);

        model.setStatus("Pending");

        log.info("WORKS when not using LinkedHashMap: {}", model);

        return repo.save(model);
    }
}
