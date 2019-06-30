package com.challenge.cargo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.cargo.model.BoosterRequest;
import com.challenge.cargo.producer.BoosterTankProducer;
import com.challenge.cargo.repository.BoosterTankRepository;

/**
 * Messaging Tests
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = "com.challenge:booster-service:+:stubs", workOffline = true)
public class BoosterServiceApplicationMessagingTests {

    @Autowired
    private StubTrigger stubTrigger;

    @Autowired
    private BoosterTankRepository boosterTankRepository;

    @Autowired
    private BoosterTankProducer boosterTankProducer;

    @Test
    public void shouldStoreResultsOfCreatingTank() {
        // Given
        final UUID uuid = UUID.fromString("e2a8b899-6b62-4010-81f1-9faed24fed2b");

        // When
        stubTrigger.trigger("create_booster_tank");

        // Then
        final String title = boosterTankRepository.getTitle(uuid);

        assertThat(title).isEqualTo("reverseTank");
    }

    @Test
    public void shouldOutputTankTitleWhenRequestingToCreateTank() {
        // Given
        boosterTankProducer.requestTank(new BoosterRequest("reverseTank"));
        final UUID uuid = UUID.fromString("e2a8b899-6b62-4010-81f1-9faed24fed2b");

        // When
        final String title = boosterTankRepository.getTitle(uuid);

        // Then
        assertThat(title).isEqualTo("reverseTank");
    }


}
