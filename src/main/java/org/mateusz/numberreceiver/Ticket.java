package org.mateusz.numberreceiver;

import java.time.LocalDateTime;
import java.util.Collection;

record Ticket(String ticketId, LocalDateTime drawDate, Collection<Integer> numbersFromUser) {
}
