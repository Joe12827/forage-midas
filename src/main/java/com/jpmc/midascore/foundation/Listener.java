package com.jpmc.midascore.foundation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.jpmc.midascore.component.DatabaseConduit;

@Component
public class Listener {
    @Autowired
    private DatabaseConduit databaseConduit;

    @KafkaListener(id = "test-group", topics = "test-topic")
	public void listen(Transaction transaction) {
        databaseConduit.process_transaction(transaction);
	}
}
