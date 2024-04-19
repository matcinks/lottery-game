package org.mateusz.domain.drawdate;

import lombok.Builder;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
record DrawDate(String id,
                       BigInteger number,
                       LocalDateTime time) {
}