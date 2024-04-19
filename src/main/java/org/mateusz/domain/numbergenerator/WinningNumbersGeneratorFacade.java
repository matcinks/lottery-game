package org.mateusz.domain.numbergenerator;

import lombok.AllArgsConstructor;
import org.mateusz.domain.drawdate.DrawDateFacade;
import org.mateusz.domain.numbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class WinningNumbersGeneratorFacade {

    private final DrawDateFacade drawDateFacade;
    private final RandomNumberGenerator winningNumbersGenerator;
    private final WinningNumbersValidator winningNumbersValidator;
    private final WinningNumbersRepository winningNumbersRepository;

    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime nextDrawDate = NumberGeneratorMapper.mapLocalDateTimeFromDrawDateDto(drawDateFacade.getNextDrawDate());
        String winningNumbersId = UUID.randomUUID().toString();
        Set<Integer> generatedWinningNumbers = winningNumbersGenerator.generateSixRandomNumbers();

        winningNumbersValidator.validate(generatedWinningNumbers);
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .id(winningNumbersId)
                .winningNumbers(generatedWinningNumbers)
                .drawDate(nextDrawDate)
                .build();
        WinningNumbers savedWinningNumbers = winningNumbersRepository.save(winningNumbers);

        return WinningNumbersDto.builder()
                .winningNumbers(savedWinningNumbers.winningNumbers())
                .drawDate(savedWinningNumbers.drawDate())
                .build();
    }
}
