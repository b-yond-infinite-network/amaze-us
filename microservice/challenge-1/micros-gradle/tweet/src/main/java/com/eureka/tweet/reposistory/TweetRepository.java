package com.eureka.tweet.reposistory;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.eureka.tweet.domain.Tweet;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.datastax.driver.core.DataType.bigint;
import static com.datastax.driver.core.DataType.text;
import static com.datastax.driver.core.DataType.uuid;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;


/**
 * Tweet repository, uses datasax driver
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@Repository
public class TweetRepository {
    private Mapper<Tweet> mapper;
    private Session session;

    private static final String TABLE = "tweets";

    public TweetRepository(MappingManager mappingManager) {
        createTable(mappingManager.getSession());
        this.mapper = mappingManager.mapper(Tweet.class);
        this.session = mappingManager.getSession();
    }

    private void createTable(Session session) {
        session.execute(
                SchemaBuilder.createTable(TABLE)
                        .ifNotExists()
                        .addPartitionKey("id", uuid())
                        .addColumn("user_id", bigint())
                        .addColumn("created_at", bigint())
                        .addColumn("content", text())
                        .addColumn("reply_pid", uuid())
                        .addColumn("reply_uid", bigint())

        )

        ;
    }

    public List<Tweet> findAll() {
        final ResultSet result = session.execute(select().all().from(TABLE));
        return mapper.map(result).all();
    }

    public List<Tweet> findAllByUserId(Long userId) {
        final ResultSet result = session.execute(select().all().from(TABLE).where(eq("user_id", userId)));
        return mapper.map(result).all();
    }

    public Tweet findByID(UUID id){
        return mapper.get(id);
    }

    public Tweet save(Tweet tweet) {
        mapper.save(tweet);
        return tweet;
    }
}
