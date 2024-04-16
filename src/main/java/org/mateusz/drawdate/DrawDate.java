package org.mateusz.drawdate;

import lombok.Builder;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
record DrawDate(String id,
                       BigInteger number,
                       LocalDateTime time) {
}