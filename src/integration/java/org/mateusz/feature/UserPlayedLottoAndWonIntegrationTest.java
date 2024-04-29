package org.mateusz.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.mateusz.BaseIntegrationTest;
import org.mateusz.domain.numbergenerator.WinningNumbersGeneratorFacade;
import org.mateusz.domain.numbergenerator.WinningNumbersNotFoundException;
import org.mateusz.domain.numberreceiver.dto.NumberReceiverResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserPlayedLottoAndWonIntegrationTest extends BaseIntegrationTest {

    @Autowired
    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;

    @Test
    public void should_user_win_and_system_should_generate_winners() throws Exception {
        // *****
        // 1. external service returns 6 random numbers
        // *****
        // given
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [1, 2, 3, 4, 5, 6, 82, 82, 83, 83, 86, 57, 10, 81, 53, 93, 50, 54, 31, 88, 15, 43, 79, 32, 43]
                                """.trim()
                        )));
        // *****
        // 2. system fetched winning numbers for date: 19.11.2022
        // *****
        // given
        LocalDateTime drawDate = LocalDateTime.of(2022, 11, 19, 12, 0, 0, 0);
        // when & then
        await()
                .atMost(Duration.ofSeconds(20))
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> {
                            try {
                                return !winningNumbersGeneratorFacade.retrieveWinningNumbersByDate(drawDate)
                                        .winningNumbers()
                                        .isEmpty();
                            } catch (WinningNumbersNotFoundException e) {
                                return false;
                            }
                        }
                );
        // *****
        // 3. user makes POST request to /winningNumbers endpoint with numbers: 1, 2, 3, 4, 5, 6 at 16.11.2022 10:00
        // *****
        // given
        // when
        ResultActions perform = mockMvc.perform(post("/inputNumbers")
                .content(
                        """
                                {
                                "inputNumbers": [1, 2, 3, 4, 5, 6]
                                }
                                """.trim()
                ).contentType(MediaType.APPLICATION_JSON)
        );
        // then
        MvcResult result = perform.andExpect(status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        NumberReceiverResponseDto numberReceiverResponseDto = objectMapper.readValue(json, NumberReceiverResponseDto.class);

        assertAll(
                () -> assertThat(numberReceiverResponseDto.ticketDto().drawDate()).isEqualTo(drawDate),
                () -> assertThat(numberReceiverResponseDto.ticketDto().ticketId()).isNotNull(),
                () -> assertThat(numberReceiverResponseDto.message()).isEqualTo("success")
        );

    }
}
