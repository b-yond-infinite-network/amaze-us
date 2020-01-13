package com.eureka.social.service;

import com.eureka.common.domain.dto.FollowDTO;
import com.eureka.social.domain.Following;
import com.eureka.social.repository.FollowingRepository;
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
public class FollowingServiceTests {

    private FollowingRepository followingRepository;
    private FollowingService followingService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        followingRepository= Mockito.mock(FollowingRepository.class);
        followingService = new FollowingService(followingRepository);

        List<Following> following = new ArrayList<>();
        Following f1 = new Following();
        f1.setDestinationId(2L);
        f1.setSourceId(1L);
        Following f2 = new Following();
        f2.setDestinationId(3L);
        f2.setSourceId(1L);

        following.add(f1);
        following.add(f2);

        Mockito.when(followingRepository.findBySourceId(1L))
                .thenReturn(following);


        Mockito.when(followingRepository.findBySourceIdAndDestinationId(1L, 2L))
                .thenReturn(java.util.Optional.of(f1));

        Mockito.when(followingRepository.save(any()))
                .thenReturn(f1);

    }

    @Test
    public void testFindBySource(){
        List<Following> follows = followingService.getFollowing(1L);
        assertFalse(follows.isEmpty());

        assertEquals(2,follows.size());
    }


    @Test
    public void testFindElement() {

        FollowDTO dto = new FollowDTO();
        dto.setDestinationId(2L);
        dto.setSourceId(1L);
        Optional<Following> follow = followingService.find(dto);
        assertTrue(follow.isPresent());
    }

    @Test
    public void testFindNoSuchElement() {

        FollowDTO dto = new FollowDTO();
        dto.setDestinationId(4L);
        dto.setSourceId(2L);
        Optional<Following> follow = followingService.find(dto);
        assertFalse(follow.isPresent());
    }

    @Test
    public void testDeleteElement() {
        FollowDTO dto = new FollowDTO();
        dto.setDestinationId(2L);
        dto.setSourceId(1L);
        followingService.delete(dto);
    }

    @Test
    public void testDeleteNoSuchElement() {
        FollowDTO dto = new FollowDTO();
        dto.setDestinationId(5L);
        dto.setSourceId(2L);
        followingService.delete(dto);
    }


    @Test
    public void testCreateElement(){
        FollowDTO dto = new FollowDTO();
        dto.setDestinationId(2L);
        dto.setSourceId(1L);

        Following follow = followingService.create(dto);
        assertSame(follow.getDestinationId(), dto.getDestinationId());
        assertSame(follow.getSourceId(), dto.getSourceId());

    }

}
