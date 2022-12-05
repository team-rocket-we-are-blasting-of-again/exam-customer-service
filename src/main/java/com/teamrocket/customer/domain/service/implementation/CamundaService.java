package com.teamrocket.customer.domain.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamrocket.customer.domain.service.ICamundaService;
import com.teamrocket.customer.domain.model.dto.StartOrderProcess;
import com.teamrocket.customer.domain.model.dto.NewOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
@Service
public class CamundaService implements ICamundaService {
    private final RestTemplate restTemplate;

    @Value("${camunda.server.engine}")
    private String restEngine;

    @Value("${camunda.server.definition.key}")
    private String processDefinitionKey;

    private final ObjectMapper mapper;

    public String startOrderProcess(String customerId, NewOrder newOrder) {
        int parsedId = Integer.parseInt(customerId);
        newOrder.setCustomerId(parsedId);

        String startProcessURL = new StringBuilder(restEngine)
                .append("process-definition/key/")
                .append(processDefinitionKey)
                .append("/start")
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        StartOrderProcess camundaRequest = null;
        try {
            camundaRequest = StartOrderProcess.builder()
                    .variables(StartOrderProcess.OrderHolder
                            .builder()
                            .order(StartOrderProcess.CamundaOrder
                                    .builder()
                                    .value(mapper.writeValueAsString(newOrder))
                                    .type("json")
                                    .build())
                            .build())
                    .build();
        } catch (JsonProcessingException exception) {
            log.error("An object mapper exception has occurred: {}",
                    "from camunda start order request",
                    new RuntimeException(exception)
            );
        }

        HttpEntity<StartOrderProcess> request =
                new HttpEntity<>(camundaRequest, headers);
        ResponseEntity<String> response =
                restTemplate.postForEntity(startProcessURL, request, String.class);

        log.info("Camunda process with id {} started", response.getBody());

        return response.getBody();
    }
}
