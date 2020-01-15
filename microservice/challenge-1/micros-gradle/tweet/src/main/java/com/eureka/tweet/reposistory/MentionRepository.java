package com.eureka.tweet.reposistory;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.eureka.tweet.domain.Mention;
import com.eureka.tweet.domain.Tweet;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.datastax.driver.core.DataType.bigint;
import static com.datastax.driver.core.DataType.set;
import static com.datastax.driver.core.DataType.text;
import static com.datastax.driver.core.DataType.uuid;

/**
 * Mention repository - uses datasax driver
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@Repository
public class MentionRepository {

    private Mapper<Mention> mapper;
    private Session session;

    private static final String TABLE = "mentions";

    public MentionRepository(MappingManager mappingManager) {
        createTable(mappingManager.getSession());
        this.mapper = mappingManager.mapper(Mention.class);
        this.session = mappingManager.getSession();
    }

    private void createTable(Session session) {
        session.execute(
                SchemaBuilder.createTable(TABLE)
                        .ifNotExists()
                        .addColumn("post_ids",set(uuid()))
                        .addPartitionKey("user_id", bigint())


        )

        ;
    }

    public Mention findByID(Long id){
        return mapper.get(id);
    }

    public Mention save(Mention tweet) {
        mapper.save(tweet);
        return tweet;
    }
}
