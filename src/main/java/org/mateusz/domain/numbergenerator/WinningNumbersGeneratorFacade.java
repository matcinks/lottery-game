package org.mateusz.domain.numbergenerator;

import lombok.AllArgsConstructor;
import org.mateusz.domain.drawdate.DrawDateFacade;
import org.mateusz.domain.numbergenerator.dto.SixRandomNumbersDto;
import org.mateusz.domain.numbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.Optional;
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
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .id(winningNumbersId)
                .winningNumbers(winningNumber)
                .drawDate(nextDrawDate)
                .build();
//        WinningNumbers savedWinningNumbers = winningNumbersRepository.save(winningNumbers);

        return WinningNumbersDto.builder()
                .winningNumbers(winningNumbers.winningNumbers())
                .drawDate(winningNumbers.drawDate())
                .build();
    }

    public WinningNumbersDto retrieveWinningNumbersByDate(LocalDateTime date) {
        WinningNumbers numbersByDate = winningNumbersRepository.findNumbersByDate(date)
                .orElseThrow(() -> new WinningNumbersNotFoundException("Winning numbers not found"));
        return WinningNumbersDto.builder()
                .winningNumbers(numbersByDate.winningNumbers())
                .drawDate(numbersByDate.drawDate())
                .build();
    }
}