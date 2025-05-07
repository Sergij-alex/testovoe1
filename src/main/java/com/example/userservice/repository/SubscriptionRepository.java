package com.example.userservice.repository;

import com.example.userservice.model.entity.Subscription;
import com.example.userservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUser(User user);

    @Query("SELECT s.serviceName, COUNT(s) as cnt FROM Subscription s GROUP BY s.serviceName ORDER BY cnt DESC")
    List<Object[]> findTopSubscriptions();
}