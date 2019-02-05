package com.challenge.suitecrm.processing.topology;

import com.challenge.suitecrm.datamodel.events.ProductEvent;
import com.challenge.suitecrm.datamodel.events.ProductEventType;
import com.challenge.suitecrm.datamodel.ingester.ProductRelationShip;
import com.challenge.suitecrm.datamodel.ingester.RawProduct;
import com.challenge.suitecrm.datamodel.storage.Link;
import com.challenge.suitecrm.datamodel.storage.Product;
import com.challenge.suitecrm.datamodel.utils.KeyProduct;
import com.challenge.suitecrm.processing.commons.AbstractTopologyBuilder;
import com.challenge.suitecrm.processing.commons.BaseConfiguration;
import com.challenge.suitecrm.processing.topology.ProductsTopologyBuilder.Configuration;
import com.google.common.collect.ImmutableMap;
import com.typesafe.config.Config;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

/**
 * This topology take data from topics {products-stream,products-relations-stream} and aggregate them to topics (table-product).
 * Then the output topics can be used as a product referentiel
 */
@Slf4j
public class ProductsTopologyBuilder extends
    AbstractTopologyBuilder<Configuration> {

  public static final String TOPOLOGY = "products";

  private KStream<KeyProduct, RawProduct> streamRawProduct;
  private KStream<KeyProduct, ProductRelationShip> streamProductProductRelationShipKStream;
  private KStream<KeyProduct, ProductEvent> streamEventsProduct;


  public ProductsTopologyBuilder(Config config) {
    super(new ProductsTopologyBuilder.Configuration(config));
  }

  @Override
  public Topology buildStreamingTopology() {
    StreamsBuilder builder = new StreamsBuilder();

    streamRawProduct = builder.stream(getConfiguration().getInputTopic("ingest_products"));
    streamProductProductRelationShipKStream = builder.stream(getConfiguration().getInputTopic("ingest_product_relations"));
    streamEventsProduct = builder.stream(getConfiguration().getInputTopic("products_events"));

    // Send a product change event
    streamRawProduct
        .mapValues(value -> ProductEvent.newBuilder()
                .setProductId(value.getProductId())
                .setEventType(ProductEventType.product_changed)
                .setProduct(value)
                .build())
        .to(getConfiguration().getInputTopic("products_events"));

    // Send a relation event
    streamProductProductRelationShipKStream
        .mapValues(value -> ProductEvent.newBuilder()
            .setProductId(value.getProductId())
            .setEventType(ProductEventType.product_linked)
            .setLinks(ImmutableMap.of(value.getTargetProductId(),value))
            .build())
        .to(getConfiguration().getInputTopic("products_events"));

    // --------- Process Product events -------------------
    streamEventsProduct
        .groupByKey()
        .aggregate(
            () -> Product.newBuilder().build(),
            (key, value, aggregate) -> {
              if(ProductEventType.product_changed.equals(value.getEventType()) && value.getProduct()!=null){
                log.debug("Update product infos {} -> {} ", key, value.getProduct());
                return Product.
                    newBuilder(Mapper.INSTANCE.as(value.getProduct()))
                    .setLinks(aggregate.getLinks())
                    .build();
              }
              if(ProductEventType.product_linked.equals(value.getEventType()) && MapUtils.isNotEmpty(value.getLinks())){
                log.debug("Update product links {} -> {} ", key, value.getLinks());
                if(aggregate.getLinks()==null) aggregate.setLinks(new HashMap<>());
                value.getLinks().entrySet()
                    .forEach(entry -> aggregate
                        .getLinks().put(
                            entry.getKey(),
                            Link.newBuilder()
                                .setTargeProductId(entry.getValue().getTargetProductId())
                                .setType(entry.getValue().getType())
                                .build()
                        ));
                return  aggregate;
              }
              return aggregate;
            },
            getMaterialized(getConfiguration().productStore))
        .toStream().to(getConfiguration().getOutputTopic("store_products"));

    return builder.build();
  }

  public static class Configuration extends BaseConfiguration {

    public final Pair<String, Boolean> productStore;

    public Configuration(final Config config) {
      super(config, TOPOLOGY);

      this.productStore = getStateStore("product_store");
    }
  }
}
