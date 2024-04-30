package org.mateusz.domain.resultannouncer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultAnnouncerRepository extends MongoRepository<Result, String> {
}