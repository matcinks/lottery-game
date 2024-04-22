package org.mateusz.feature;

import org.junit.jupiter.api.Test;
import org.mateusz.BaseIntegrationTest;
import org.mateusz.domain.numbergenerator.RandomNumberGenerable;
import org.mateusz.domain.numbergenerator.dto.SixRandomNumbersDto;
import org.springframework.beans.factory.annotation.Autowired;

public class UserPlayedLottoAndWonIntegrationTest extends BaseIntegrationTest {

    @Autowired
    RandomNumberGenerable randomNumberGenerable;

    @Test
    public void should_user_win_and_system_should_generate_winners() {
        SixRandomNumbersDto sixRandomNumbersDto = randomNumberGenerable.generateSixRandomNumbers();
    }
}
