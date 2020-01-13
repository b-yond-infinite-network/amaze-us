package com.eureka.social.repository;

import com.eureka.social.domain.Followers;
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
public class FollowersRepositoryTests {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FollowersRepository followersRepository;


    @Test
    public void whenFindBySourceAndDest_thenReturnFollower() {
        // given
        Followers f = new Followers();
        f.setSourceId(1L);
        f.setDestinationId(2L);
        entityManager.persist(f);
        entityManager.flush();

        // when
        Optional<Followers> found = followersRepository.findBySourceIdAndDestinationId(1L, 2L);

        // then
        assertThat(found.get().getDestinationId())
                .isEqualTo(f.getDestinationId());

        assertThat(found.get().getSourceId())
                .isEqualTo(f.getSourceId());
    }



    @Test
    public void whenFindBySourceAndDest_notFound() {

        // when
        Optional<Followers> found = followersRepository.findBySourceIdAndDestinationId(2L, 3L);

        // then
        assertTrue(found.isEmpty());
    }


    @Test
    public void whenFindByDest_thenReturnFollowers() {
        // given
        Followers f = new Followers();
        f.setSourceId(10L);
        f.setDestinationId(21L);
        entityManager.persist(f);
        entityManager.flush();
        Followers f1 = new Followers();
        f1.setSourceId(11L);
        f1.setDestinationId(21L);
        entityManager.persist(f1);
        entityManager.flush();

        // when
        List<Followers> found = followersRepository.findByDestinationId(21L);

        assertFalse(found.isEmpty());

        assertEquals(2, found.size());

    }

    @Test
    public void whenFindByDest_notFound() {

        // when
        List<Followers> found = followersRepository.findByDestinationId(3L);

        // then
        assertTrue(found.isEmpty());
    }
}
