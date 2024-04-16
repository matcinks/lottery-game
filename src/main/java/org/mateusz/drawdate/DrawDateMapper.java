package org.mateusz.drawdate;

import org.mateusz.drawdate.dto.DrawDateDto;

import java.time.LocalDateTime;

class DrawDateMapper {

    public static DrawDateDto mapFromDrawDate(DrawDate drawDate) {
        return DrawDateDto.builder()
                .id(drawDate.id())
                .time(drawDate.time())
                .build();
    }

    public static DrawDate mapFromDrawDateDto(DrawDateDto drawDatedto) {
        return DrawDate.builder()
                .id(drawDatedto.id())
                .time(drawDatedto.time())
                .build();
    }

    public static LocalDateTime timeFromDrawDateDto(DrawDateDto drawDateDto) {
        return mapFromDrawDateDto(drawDateDto).time();
    }
}
