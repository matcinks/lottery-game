package org.mateusz.numbergenerator;

import org.mateusz.drawdate.dto.DrawDateDto;

import java.time.LocalDateTime;

class DrawDateMapper {
    public static LocalDateTime localDateTimeFromDrawDateDto(DrawDateDto drawDateDto) {
        return drawDateDto.time();
    }

}
