package org.mateusz.domain.drawdate;

import org.mateusz.domain.drawdate.dto.DrawDateDto;

import java.time.LocalDateTime;

class DrawDateMapper {

    public static DrawDateDto mapFromDrawDate(DrawDate drawDate) {
        return DrawDateDto.builder()
                .id(drawDate.id())
                .time(drawDate.date())
                .build();
    }

    public static DrawDate mapFromDrawDateDto(DrawDateDto drawDatedto) {
        return DrawDate.builder()
                .id(drawDatedto.id())
                .date(drawDatedto.time())
                .build();
    }

    public static LocalDateTime timeFromDrawDateDto(DrawDateDto drawDateDto) {
        return mapFromDrawDateDto(drawDateDto).date();
    }
}
