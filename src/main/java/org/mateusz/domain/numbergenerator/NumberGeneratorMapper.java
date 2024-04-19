package org.mateusz.domain.numbergenerator;

import org.mateusz.domain.drawdate.dto.DrawDateDto;

import java.time.LocalDateTime;

class NumberGeneratorMapper {
    public static LocalDateTime mapLocalDateTimeFromDrawDateDto(DrawDateDto drawDateDto) {
        return drawDateDto.time();
    }

}
