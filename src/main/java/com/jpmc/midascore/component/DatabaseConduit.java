package com.jpmc.midascore.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;

@Component
public class DatabaseConduit {
    static final Logger logger = LoggerFactory.getLogger(DatabaseConduit.class);

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public DatabaseConduit(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }

    public void save(TransactionRecord transactionRecord) {
        transactionRepository.save(transactionRecord);
    }

    public void process_transaction(Transaction transaction) {
        logger.info("Processing transaction: " + transaction);
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());
        if (sender == null || recipient == null) {
            logger.error("Sender or recipient not found for transaction");
            logger.info("----");
            return;
        }
        if (sender.getBalance() < transaction.getAmount()) {
            logger.error("Insufficient funds for transaction");
            logger.info("----");
            return;
        }
        sender.setBalance(sender.getBalance() - transaction.getAmount());
        recipient.setBalance(recipient.getBalance() + transaction.getAmount());
        userRepository.save(sender);
        userRepository.save(recipient);
        transactionRepository.save(new TransactionRecord(transaction.getSenderId(), transaction.getRecipientId(), transaction.getAmount()));
        
        logger.info("Updated sender:    " + sender);
        logger.info("Updated recipient: " + recipient);
        logger.info("----");

    }
}
