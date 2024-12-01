package com.makeorder.core.payment.entity.converter;

import com.makeorder.core.payment.entity.enums.PaymentMethodType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PaymentMethodTypeConverter implements AttributeConverter<PaymentMethodType, String> {

    @Override
    public String convertToDatabaseColumn(PaymentMethodType attribute) {
        return attribute.getDbCode();
    }

    @Override
    public PaymentMethodType convertToEntityAttribute(String dbData) {
        return PaymentMethodType.fromDbCode(dbData);
    }
}