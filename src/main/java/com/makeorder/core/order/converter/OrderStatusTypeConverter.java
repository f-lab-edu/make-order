package com.makeorder.core.order.converter;

import com.makeorder.core.order.entity.enums.OrderStatusType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class OrderStatusTypeConverter implements AttributeConverter<OrderStatusType, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatusType attribute) {
        return attribute.getDbCode();
    }

    @Override
    public OrderStatusType convertToEntityAttribute(String dbData) {
        return OrderStatusType.fromDbCode(dbData);
    }
}