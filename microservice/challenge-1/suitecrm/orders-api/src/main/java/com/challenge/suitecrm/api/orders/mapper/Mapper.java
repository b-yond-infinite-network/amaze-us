package com.challenge.suitecrm.api.orders.mapper;

import com.challenge.suitecrm.api.orders.model.Order;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
public interface Mapper {

    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

    Order as(com.challenge.suitecrm.cassandra.table.Order order);

}
