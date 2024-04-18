package org.mateusz.numbergenerator;

import lombok.AllArgsConstructor;
import org.mateusz.drawdate.DrawDateFacade;
import org.mateusz.numbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class WinningNumbersGeneratorFacade {

    private final DrawDateFacade drawDateFacade;
    private final WinningNumbersGenerator winningNumbersGenerator;
    private final WinningNumbersValidator winningNumbersValidator;
    private final WinningNumbersRepository winningNumbersRepository;

    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime nextDrawDate = DrawDateMapper.localDateTimeFromDrawDateDto(drawDateFacade.getNextDrawDate());
        String winningNumbersId = UUID.randomUUID().toString();
        Set<Integer> generatedWinningNumbers = winningNumbersGenerator.generateSixRandomNumbers();

        winningNumbersValidator.validate(generatedWinningNumbers);
        WinningNumbers winningNumbers = winningNumbersRepository.save(new WinningNumbers(winningNumbersId, generatedWinningNumbers, nextDrawDate));

        return WinningNumbersDto.builder()
                .winningNumbers(winningNumbers.winningNumbers())
                .drawDate(winningNumbers.drawDate())
                .build();
    }
}
