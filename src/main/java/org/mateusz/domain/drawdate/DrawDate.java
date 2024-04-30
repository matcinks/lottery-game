package org.mateusz.domain.drawdate;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
@Document
record DrawDate(
        @Id
        String id,
        BigInteger number,
        LocalDateTime date) {
}