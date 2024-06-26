package org.mateusz.domain.numberreceiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NumberReceiverRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findAllByDate(LocalDateTime date);
}
