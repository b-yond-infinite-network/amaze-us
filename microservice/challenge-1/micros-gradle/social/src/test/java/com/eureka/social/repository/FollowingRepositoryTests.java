package com.eureka.social.repository;

import com.eureka.social.domain.Followers;
import com.eureka.social.domain.Following;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FollowingRepositoryTests {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FollowingRepository followingRepository;


    @Test
    public void whenFindBySourceAndDest_thenReturnFollower() {
        // given
        Following f = new Following();
        f.setSourceId(1L);
        f.setDestinationId(2L);
        entityManager.persist(f);
        entityManager.flush();

        // when
        Optional<Following> found = followingRepository.findBySourceIdAndDestinationId(1L, 2L);

        // then
        assertThat(found.get().getDestinationId())
                .isEqualTo(f.getDestinationId());

        assertThat(found.get().getSourceId())
                .isEqualTo(f.getSourceId());
    }


    @Test
    public void whenFindBySourceAndDest_notFound() {

        // when
        Optional<Following> found = followingRepository.findBySourceIdAndDestinationId(2L, 3L);

        // then
        assertTrue(found.isEmpty());
    }

    @Test
    public void whenFindBySource_thenReturnFollowers() {
        // given
        Following f = new Following();
        f.setSourceId(10L);
        f.setDestinationId(21L);
        entityManager.persist(f);
        entityManager.flush();
        Following f1 = new Following();
        f1.setSourceId(11L);
        f1.setDestinationId(21L);
        entityManager.persist(f1);
        entityManager.flush();

        // when
        List<Following> found = followingRepository.findBySourceId(11L);

        assertFalse(found.isEmpty());

        assertEquals(1, found.size());

    }

    @Test
    public void whenFindByDest_notFound() {

        // when
        List<Following> found = followingRepository.findBySourceId(3L);

        // then
        assertTrue(found.isEmpty());
    }


}
