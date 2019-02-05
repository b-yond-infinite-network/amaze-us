package com.challenge.suitecrm.api.customers.mapper;

import com.challenge.suitecrm.api.customers.model.Customer;
import com.challenge.suitecrm.api.customers.model.Recommendation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
public interface Mapper {

    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

    Customer as(com.challenge.suitecrm.cassandra.table.Customer customer);

    Recommendation as (com.challenge.suitecrm.cassandra.table.Recommendation recommendation);

    default List<String> mapProduiToListString ( Map<String,String> map){
        if(map==null) return null;
        return new ArrayList<>(map.keySet());
    }

}
