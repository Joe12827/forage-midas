package com.jpmc.midascore.foundation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {
    static final Logger logger = LoggerFactory.getLogger(Listener.class);

    @KafkaListener(id = "test-group", topics = "test-topic")
	public void listen(Transaction transaction) {
		logger.info("Listener Received: " + transaction);
	}
}
