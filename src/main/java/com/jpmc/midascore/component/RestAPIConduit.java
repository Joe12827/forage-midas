package com.jpmc.midascore.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;

@Component
public class RestAPIConduit {
    static final Logger logger = LoggerFactory.getLogger(RestAPIConduit.class);

    private final RestTemplate restTemplate;

    public RestAPIConduit(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public int get_incentive_points(Transaction transaction) {
        String url = "http://localhost:8080/incentive";
        Incentive incentive = restTemplate.postForObject(url, transaction, Incentive.class);
        logger.info("Incentive API raw response: " + incentive);
        Integer points = (incentive != null) ? incentive.getAmount() : null;
        return points;
    }

}
