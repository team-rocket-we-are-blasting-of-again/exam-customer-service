package com.teamrocket.customer.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamrocket.customer.dto.CamundaStartOrderProcess;
import com.teamrocket.customer.dto.NewOrder;
import com.teamrocket.customer.service.ICamundaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class CamundaService implements ICamundaService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${camunda.server.engine}")
    private String restEngine;

    @Value("${camunda.server.definition.key}")
    private String processDefinitionKey;

    @Autowired
    private ObjectMapper mapper;

    public String startOrderProcess(String customerId, NewOrder newOrder) throws JsonProcessingException {
        int parsedId = Integer.parseInt(customerId);
        newOrder.setCustomerId(parsedId);

        String startProcessURL = new StringBuilder(restEngine)
                .append("process-definition/key/")
                .append(processDefinitionKey)
                .append("/start")
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CamundaStartOrderProcess camundaRequest = CamundaStartOrderProcess.builder()
                .variables(CamundaStartOrderProcess.OrderHolder
                        .builder()
                        .order(CamundaStartOrderProcess.CamundaOrder
                                .builder()
                                .value(mapper.writeValueAsString(newOrder))
                                .type("json")
                                .build())
                        .build())
                .build();

        HttpEntity<CamundaStartOrderProcess> request =
                new HttpEntity<>(camundaRequest, headers);
        ResponseEntity<String> response =
                restTemplate.postForEntity(startProcessURL, request, String.class);

        log.info("Camunda process with id {} started", response.getBody());

        return response.getBody();
    }
}
