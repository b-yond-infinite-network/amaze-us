package com.eureka.tweet.service;

import com.eureka.tweet.domain.Mention;
import com.eureka.tweet.reposistory.MentionRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class MentionService {

    private MentionRepository mentionRepository;

    public MentionService(MentionRepository mentionRepository) {
        this.mentionRepository = mentionRepository;
    }

    public Optional<Mention> createMention(Long uid, UUID post){

        Optional<Mention> found = find(uid);

        if (found.isPresent()){
            found.get().getPostIds().add(post);
            return Optional.of(mentionRepository.save(found.get()));
        }

        Mention m = new Mention();
        m.setUserId(uid);
        Set<UUID> uids = new HashSet<>();
        uids.add(post);
        m.setPostIds(uids);
        return Optional.of(mentionRepository.save(m));
    }

    public Optional<Mention> find(Long id){
        return Optional.ofNullable(mentionRepository.findByID(id));
    }

}
