package org.mateusz.domain.drawdate;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DrawDateRepository extends MongoRepository<DrawDate, String> {

    Optional<DrawDate> findByDate(LocalDateTime date);

    Optional<DrawDate> findFirstByOrderByNumberDesc();
}
