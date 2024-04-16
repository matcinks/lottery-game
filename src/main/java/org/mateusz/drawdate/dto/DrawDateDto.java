package org.mateusz.drawdate.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DrawDateDto(String id,
                          LocalDateTime time) {
}
