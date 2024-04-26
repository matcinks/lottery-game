package org.mateusz.domain.numbergenerator;

import lombok.AllArgsConstructor;
import org.mateusz.domain.drawdate.DrawDateFacade;
import org.mateusz.domain.numbergenerator.dto.SixRandomNumbersDto;
import org.mateusz.domain.numbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class WinningNumbersGeneratorFacade {

    private final DrawDateFacade drawDateFacade;
    private final RandomNumberGenerable randomGenerable;
    private final WinningNumbersValidator winningNumbersValidator;
    private final WinningNumbersRepository winningNumbersRepository;
    private final WinningNumbersGeneratorFacadeConfigurationProperties properties;

    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime nextDrawDate = NumberGeneratorMapper.mapLocalDateTimeFromDrawDateDto(drawDateFacade.getNextDrawDate());
        String winningNumbersId = UUID.randomUUID().toString();
        SixRandomNumbersDto winningNumbersDto = randomGenerable.generateSixRandomNumbers(properties.lowerBand(), properties.upperBand(), properties.count());
        Set<Integer> winningNumber = winningNumbersDto.numbers();
        winningNumbersValidator.validate(winningNumber);
        WinningNumbers winningNumbersDocument = WinningNumbers.builder()
                .id(winningNumbersId)
                .winningNumbers(winningNumber)
                .date(nextDrawDate)
                .build();
        WinningNumbers savedWinningNumbers = winningNumbersRepository.save(winningNumbersDocument);
        return WinningNumbersDto.builder()
                .winningNumbers(savedWinningNumbers.winningNumbers())
                .drawDate(savedWinningNumbers.date())
                .build();
    }

    public WinningNumbersDto retrieveWinningNumbersByDate(LocalDateTime date) {
        WinningNumbers numbersByDate = winningNumbersRepository.findNumbersByDate(date)
                .orElseThrow(() -> new WinningNumbersNotFoundException("Winning numbers not found"));
        return WinningNumbersDto.builder()
                .winningNumbers(numbersByDate.winningNumbers())
                .drawDate(numbersByDate.date())
                .build();
    }
}