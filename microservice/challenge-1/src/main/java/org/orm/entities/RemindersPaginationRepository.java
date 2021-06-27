package org.orm.entities;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemindersPaginationRepository extends PagingAndSortingRepository<Reminders, Integer> {
}