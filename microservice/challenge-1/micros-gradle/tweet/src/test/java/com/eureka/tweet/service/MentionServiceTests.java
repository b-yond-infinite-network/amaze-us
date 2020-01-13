package com.eureka.tweet.service;

import com.eureka.tweet.domain.Mention;
import com.eureka.tweet.reposistory.MentionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;

@RunWith(MockitoJUnitRunner.class)
public class MentionServiceTests {

    private MentionService mentionService;
    private MentionRepository mentionRepository;

    @Before
    public void setUp() throws Exception {
        mentionRepository = Mockito.mock(MentionRepository.class);
        mentionService = new MentionService(mentionRepository);

        UUID uid1 = UUID.fromString("f0d12829-cb9b-4be9-837d-d75e931ba899");
        UUID uid2 = UUID.fromString("f0d12829-cb9b-4be9-837d-d75e931ba898");

        Mention m = new Mention();
        m.setUserId(1L);
        Set<UUID> uids = new HashSet<>();
        uids.add(uid1);
        uids.add(uid2);
        m.setPostIds(uids);


        Mention m2 = new Mention();
        m2.setUserId(3L);
        Set<UUID> uids2 = new HashSet<>();
        m2.setPostIds(uids2);

        Mockito.when(mentionRepository.findByID(1L)).thenReturn(m);
        Mockito.when(mentionRepository.save(any()))
        .thenReturn(m);
        Mockito.when(mentionRepository.save(argThat((Mention aBar) -> aBar.getUserId() == 3L)))
                .thenReturn(m2);

        //argThat((Tweet aBar) -> aBar.getUserId() == 1L && aBar.getContent().equals("Hello world test")


        Optional<Mention> value = Optional.empty();

       // Mockito.when(mentionService.find(3L)).thenReturn(value);

    }


    @Test
    public void testSuchFindElement() {

        Optional<Mention> found = mentionService.find(1L);
        assertThat(found.isEmpty()).isFalse();
        assertEquals(1L,(long) found.get().getUserId());

    }

    @Test
    public void testCreateMention_foundRecord() {

        UUID uid3 = UUID.fromString("f0d12829-cb9b-4be9-837d-d75e931ba890");


        Optional<Mention> found = mentionService.createMention(1L,uid3);
        assertThat(found.isEmpty()).isFalse();
        assertEquals(1L,(long) found.get().getUserId());

    }

    @Test
    public void testCreateMention_newRecord() {

        UUID uid3 = UUID.fromString("f0d12829-cb9b-4be9-837d-d75e931ba896");

        Optional<Mention> found = mentionService.createMention(3L,uid3);
        assertThat(found.isEmpty()).isFalse();
        assertEquals(3L,(long) found.get().getUserId());


    }


}
