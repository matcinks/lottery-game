package org.mateusz.numbergenerator;

import org.mateusz.drawdate.dto.DrawDateDto;

import java.time.LocalDateTime;

class NumberGeneratorMapper {
    public static LocalDateTime mapLocalDateTimeFromDrawDateDto(DrawDateDto drawDateDto) {
        return drawDateDto.time();
    }

}
