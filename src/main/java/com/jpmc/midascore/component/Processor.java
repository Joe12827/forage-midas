package com.jpmc.midascore.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;


@Component
public class Processor {
    static final Logger logger = LoggerFactory.getLogger(Processor.class);

    private final RestAPIConduit restAPIConduit;
    private final DatabaseConduit databaseConduit;

    public Processor(RestAPIConduit restAPIConduit, DatabaseConduit databaseConduit) {
        this.restAPIConduit = restAPIConduit;
        this.databaseConduit = databaseConduit;
    }

    public void process_transaction(Transaction transaction) {
        logger.info("Processing transaction: " + transaction);
        UserRecord sender = databaseConduit.findUserById(transaction.getSenderId());
        UserRecord recipient = databaseConduit.findUserById(transaction.getRecipientId());

        if (sender == null || recipient == null) {
            logger.error("Sender or recipient not found for transaction");
            logger.info("----");
            return;
        }
        if (sender.getBalance().getAmount() < transaction.getAmount()) {
            logger.error("Insufficient funds for transaction");
            logger.info("----");
            return;
        }

        float incentiveAmount = restAPIConduit.get_incentive_points(transaction);
        logger.info("Incentive points for recipient (userId=" + recipient.getName() + "): " + incentiveAmount);

        sender.setBalance(sender.getBalance().getAmount() - transaction.getAmount());
        recipient.setBalance(recipient.getBalance().getAmount() + transaction.getAmount() + incentiveAmount);
        databaseConduit.save(sender);
        databaseConduit.save(recipient);
        databaseConduit.save(new TransactionRecord(transaction.getSenderId(), transaction.getRecipientId(), transaction.getAmount() + incentiveAmount));

        
        logger.info("Updated sender:    " + sender);
        logger.info("Updated recipient: " + recipient);
        logger.info("----");
    }
}
