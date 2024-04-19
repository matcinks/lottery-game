package org.mateusz.resultannouncer.dto;

import lombok.Builder;

@Builder
public record ResultAnnouncerResponseDto(ResultDto resultDto,
                                         String message) {

}