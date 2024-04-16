package org.mateusz.numberreceiver;

import org.mateusz.drawdate.dto.DrawDateDto;

import java.time.LocalDateTime;

public class DrawDateMapper {

    public static LocalDateTime localDateTimeFromDrawDateDto(DrawDateDto drawDateDto) {
        return drawDateDto.time();
    }
}
