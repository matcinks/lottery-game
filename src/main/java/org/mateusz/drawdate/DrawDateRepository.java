package org.mateusz.drawdate;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DrawDateRepository {

    DrawDate save(DrawDate drawDate);
    List<DrawDate> findAll();
    Optional<DrawDate> findByDate(LocalDateTime time);
    Optional<BigInteger> findLastNumber();
}
