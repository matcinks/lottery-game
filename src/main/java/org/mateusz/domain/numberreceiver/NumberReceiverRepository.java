package org.mateusz.domain.numberreceiver;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NumberReceiverRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findAllByDrawDate(LocalDateTime date);
}
