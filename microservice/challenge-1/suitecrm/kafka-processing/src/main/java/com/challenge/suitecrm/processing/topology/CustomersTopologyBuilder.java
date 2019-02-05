package com.challenge.suitecrm.processing.topology;

import com.challenge.suitecrm.datamodel.storage.Link;
import com.challenge.suitecrm.datamodel.storage.Order;
import com.challenge.suitecrm.datamodel.storage.Product;
import com.challenge.suitecrm.datamodel.storage.Recommendation;
import com.challenge.suitecrm.datamodel.utils.KeyCustomer;
import com.challenge.suitecrm.datamodel.utils.KeyOrder;
import com.challenge.suitecrm.datamodel.utils.KeyProduct;
import com.challenge.suitecrm.datamodel.utils.KeyRecommendation;
import com.challenge.suitecrm.datamodel.utils.ProductRelationTypeEnum;
import com.challenge.suitecrm.processing.commons.AbstractTopologyBuilder;
import com.challenge.suitecrm.processing.commons.BaseConfiguration;
import com.challenge.suitecrm.processing.topology.CustomersTopologyBuilder.Configuration;
import com.google.common.collect.ImmutableMap;
import com.typesafe.config.Config;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;

/**
 * This topology take data from topic orders-stream and then create recommendations based on the link between produts (COMPLEMENTARY)
 */
@Slf4j
public class CustomersTopologyBuilder extends
    AbstractTopologyBuilder<Configuration> {

  public static final String TOPOLOGY = "customers";

  private KStream<KeyOrder, Order> streamOrders;
  private GlobalKTable<KeyProduct, Product> productGlobalKTable;


  public CustomersTopologyBuilder(Config config) {
    super(new CustomersTopologyBuilder.Configuration(config));
  }

  @Override
  public Topology buildStreamingTopology() {
    StreamsBuilder builder = new StreamsBuilder();

    streamOrders = builder.stream(getConfiguration().getInputTopic("ingest_orders"));
    productGlobalKTable = builder.globalTable(getConfiguration().getInputTopic("table_products"),
        getMaterialized(getConfiguration().productStore));


    //  Join the orders stream to the products and calculate customer's recommendations based on the product relationshop
    streamOrders
        .selectKey((key, value) -> KeyCustomer.newBuilder().setCustomerId(key.getCustomerId()).build())
        .join(productGlobalKTable,
        (keyCustomer, order) -> KeyProduct.newBuilder().setProductId(order.getProductId()).build(),
        (order, product) -> {
          Recommendation recommendation = Recommendation.newBuilder()
              .setCustomerId(order.getCustomerId())
              .setTimestamp(Timestamp.valueOf(LocalDateTime.now()).toString())
              .setProducts(ImmutableMap.of())
              .build();

          if(MapUtils.isNotEmpty(product.getLinks())){
            Map<String, String> complementaries = product.getLinks().values()
                .stream()
                .filter(link -> ProductRelationTypeEnum.COMPLEMENTARY.name().equalsIgnoreCase(link.getType()))
                .collect(Collectors.toMap(Link::getTargeProductId, Link::getType));

            recommendation.setProducts(complementaries);
          }
          return  recommendation;
        })
        .filter((key, value) -> MapUtils.isNotEmpty(value.getProducts()))
        .selectKey((key, value) -> KeyRecommendation
            .newBuilder()
            .setCustomerId(key.getCustomerId())
            .setTimestamp(value.getTimestamp())
            .build())
        .to(getConfiguration().getOutputTopic("recommendation"));

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
