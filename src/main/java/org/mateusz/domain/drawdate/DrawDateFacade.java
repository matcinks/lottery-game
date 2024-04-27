package org.mateusz.domain.drawdate;

import lombok.AllArgsConstructor;
import org.mateusz.domain.drawdate.dto.DrawDateDto;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Clock;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
public class DrawDateFacade {

    private final Clock clock;
    private final DrawDateCalculator drawDateCalculator;
    private final DrawDateGenerator drawDateGenerator;
    private final DrawDateRepository repository;

    public DrawDateDto getNextDrawDate() {
        LocalDateTime currentTime = LocalDateTime.now(clock);
        return getNextDrawDate(currentTime);
    }

    public DrawDateDto getNextDrawDate(LocalDateTime passedDate) {
        LocalDateTime expectedNextDrawDate = drawDateCalculator.calculateNextDrawDate(passedDate);
        Optional<DrawDate> storedNextDrawDate = repository.findByDate(expectedNextDrawDate);
        if (storedNextDrawDate.isPresent()) {
            return DrawDateMapper.mapFromDrawDate(storedNextDrawDate.get());
        } else {
            BigInteger drawDateNumber = nextDrawDateNumber();
            DrawDate generatedDrawDate = drawDateGenerator.generateDrawDate(expectedNextDrawDate, drawDateNumber);
            DrawDate savedDrawDate = repository.save(generatedDrawDate);
            return DrawDateMapper.mapFromDrawDate(savedDrawDate);
        }
    }

    public DrawDateDto getPreviousDrawDate() {
        LocalDateTime currentTime = LocalDateTime.now(clock);
        return getPreviousDrawDate(currentTime);
    }

    public DrawDateDto getPreviousDrawDate(LocalDateTime date) {
        LocalDateTime previousDate = drawDateCalculator.calculatePreviousDrawDate(date);
        Optional<DrawDate> potentialPreviousDate = repository.findByDate(previousDate);
        if (potentialPreviousDate.isPresent()) {
            return DrawDateMapper.mapFromDrawDate(potentialPreviousDate.get());
        } else {
            throw new NoSuchElementException("No DrawDate found for the given date");
        }
    }

    public List<DrawDateDto> retrieveAllDrawDates() {
        List<DrawDate> allDrawDates = repository.findAll();
        return allDrawDates.stream()
                .map(DrawDateMapper::mapFromDrawDate)
                .toList();
    }

    private BigInteger nextDrawDateNumber() {
        return repository.findFirstByOrderByNumberDesc()
                .map(drawDate -> drawDate.number().add(BigInteger.ONE))
                .orElse(BigInteger.ONE);
    }
}