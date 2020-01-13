package com.eureka.social.service;

import com.eureka.common.domain.dto.FollowDTO;
import com.eureka.social.domain.Followers;
import com.eureka.social.repository.FollowersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class FollowersServiceTests {

    private FollowersRepository followersRepository;

    private FollowersService followersService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        followersRepository= Mockito.mock(FollowersRepository.class);
        followersService = new FollowersService(followersRepository);

        List<Followers> followers = new ArrayList<>();
        Followers f1 = new Followers();
        f1.setDestinationId(1L);
        f1.setSourceId(2L);
        Followers f2 = new Followers();
        f2.setDestinationId(1L);
        f2.setSourceId(3L);

        followers.add(f1);
        followers.add(f2);

        Mockito.when(followersRepository.findByDestinationId(1L))
                .thenReturn(followers);
        Mockito.when(followersRepository.findBySourceIdAndDestinationId(2L, 1L))
                .thenReturn(Optional.of(f1));
        Mockito.when(followersRepository.save(any()))
                .thenReturn(f1);
    }

    @Test
    public void testGetFollowers() {
        List<Followers> follows = followersService.getFollowers(1L);
        assertFalse(follows.isEmpty());

        assertEquals(2,follows.size());

    }


    @Test
    public void testFindElement() {

        FollowDTO dto = new FollowDTO();
        dto.setDestinationId(1L);
        dto.setSourceId(2L);
        Optional<Followers> follow = followersService.find(dto);
        assertTrue(follow.isPresent());
    }

    @Test
    public void testFindNoSuchElement() {

        FollowDTO dto = new FollowDTO();
        dto.setDestinationId(4L);
        dto.setSourceId(2L);
        Optional<Followers> follow = followersService.find(dto);
        assertFalse(follow.isPresent());
    }

    @Test
    public void testDeleteElement() {
        FollowDTO dto = new FollowDTO();
        dto.setDestinationId(1L);
        dto.setSourceId(2L);
        followersService.delete(dto);
    }

    @Test
    public void testDeleteNoSuchElement() {
        FollowDTO dto = new FollowDTO();
        dto.setDestinationId(5L);
        dto.setSourceId(2L);
        followersService.delete(dto);
    }

    @Test
    public void testCreateElement(){
        FollowDTO dto = new FollowDTO();
        dto.setDestinationId(1L);
        dto.setSourceId(2L);

        Followers follow = followersService.create(dto);
        assertSame(follow.getDestinationId(), dto.getDestinationId());
        assertSame(follow.getSourceId(), dto.getSourceId());

    }
}
