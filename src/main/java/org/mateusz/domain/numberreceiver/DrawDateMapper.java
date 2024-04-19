package org.mateusz.domain.numberreceiver;

import org.mateusz.domain.drawdate.dto.DrawDateDto;

import java.time.LocalDateTime;

class DrawDateMapper {

    public static LocalDateTime drawDateTimeFromDrawDateDto(DrawDateDto drawDateDto) {
        return drawDateDto.time();
    }
}
