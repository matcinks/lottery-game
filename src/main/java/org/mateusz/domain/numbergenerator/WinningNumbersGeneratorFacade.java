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

    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime nextDrawDate = NumberGeneratorMapper.mapLocalDateTimeFromDrawDateDto(drawDateFacade.getNextDrawDate());
        String winningNumbersId = UUID.randomUUID().toString();
        SixRandomNumbersDto winningNumbersDto = randomGenerable.generateSixRandomNumbers();
        Set<Integer> winningNumber = winningNumbersDto.numbers();
        winningNumbersValidator.validate(winningNumber);
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .id(winningNumbersId)
                .winningNumbers(winningNumber)
                .drawDate(nextDrawDate)
                .build();
        WinningNumbers savedWinningNumbers = winningNumbersRepository.save(winningNumbers);

        return WinningNumbersDto.builder()
                .winningNumbers(savedWinningNumbers.winningNumbers())
                .drawDate(savedWinningNumbers.drawDate())
                .build();
    }
}