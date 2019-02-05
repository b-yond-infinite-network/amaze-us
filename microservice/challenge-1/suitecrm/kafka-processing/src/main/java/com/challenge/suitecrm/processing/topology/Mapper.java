package com.challenge.suitecrm.processing.topology;


import com.challenge.suitecrm.datamodel.ingester.RawProduct;
import com.challenge.suitecrm.datamodel.storage.Product;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
public interface Mapper {

  Mapper INSTANCE = Mappers.getMapper(Mapper.class);

  @Mapping(ignore = true, target = "links")
  Product as (RawProduct source);

}
