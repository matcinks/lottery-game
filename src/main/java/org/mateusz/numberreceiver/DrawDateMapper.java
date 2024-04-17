package org.mateusz.numberreceiver;

import org.mateusz.drawdate.dto.DrawDateDto;

import java.time.LocalDateTime;

class DrawDateMapper {

    public static LocalDateTime drawDateTimeFromDrawDateDto(DrawDateDto drawDateDto) {
        return drawDateDto.time();
    }
}