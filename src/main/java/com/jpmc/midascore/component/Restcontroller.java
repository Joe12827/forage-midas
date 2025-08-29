package com.jpmc.midascore.component;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;

@RestController
@RequestMapping("/")
public class Restcontroller {
    private final DatabaseConduit databaseConduit;

    public Restcontroller(DatabaseConduit databaseConduit) {
        this.databaseConduit = databaseConduit;
    }

    @GetMapping("/balance") // http://localhost:33400/balance?userId={}
    public Balance get_balance(@RequestParam String userId) {
        UserRecord user = databaseConduit.findUserById(Long.valueOf(userId));
        if (user == null) {
            return new Balance(0);
        }
        return user.getBalance();
    }
}
