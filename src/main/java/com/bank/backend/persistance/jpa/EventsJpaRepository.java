package com.bank.backend.persistance.jpa;

import com.bank.backend.persistance.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsJpaRepository extends JpaRepository<EventEntity,Long> {

}
