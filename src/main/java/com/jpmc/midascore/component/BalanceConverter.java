package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Balance;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BalanceConverter implements AttributeConverter<Balance, Float> {

    @Override
    public Float convertToDatabaseColumn(Balance balance) {
        if (balance == null) {
            return null;
        }
        return balance.getAmount();
    }

    @Override
    public Balance convertToEntityAttribute(Float dbData) {
        if (dbData == null) {
            return null;
        }
        return new Balance(dbData);
    }
    
}
