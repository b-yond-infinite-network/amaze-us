package com.eureka.auth.tests.repository;

import com.eureka.auth.domain.User;
import com.eureka.auth.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void whenFindByName_thenReturnUser() {
        // given
        User alex = new User();
        alex.setUsername("alex");
        alex.setPassword("12345");
        entityManager.persist(alex);
        entityManager.flush();

        // when
        Optional<User> found = userRepository.findOneByUsername(alex.getUsername());

        // then
        assertThat(found.get().getUsername())
                .isEqualTo(alex.getUsername());
    }

    @Test
    public void whenFindById_thenReturnUser() {
        // given
        User alex = new User();
        alex.setUsername("admin");
        alex.setPassword("12345");
        entityManager.persist(alex);
        entityManager.flush();

        // when
        Optional<User> found = userRepository.findById(1L);

        // then
        assertThat(found.get().getUsername())
                .isEqualTo(alex.getUsername());
    }

    @Test
    public void whenFindByName_thenNotFound() {
        // when
        Optional<User> found = userRepository.findOneByUsername("tesy");

        // then
        assertTrue(found.isEmpty());

    }

    @Test
    public void whenFindById_thenNotFound() {

        // when
        Optional<User> found = userRepository.findById(5L);

        assertTrue(found.isEmpty());

    }
}
