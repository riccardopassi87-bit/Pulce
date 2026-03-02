package com.pulce.pulcebackend.repository;

import com.pulce.pulcebackend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer>
{
    List<Item> findByNameContainingIgnoreCase(String name);
    List<Item> findByType(String type);
    List<Item> findByNameContainingIgnoreCaseAndType(String name, String type);

    // Notification Scheduler
    @Query(value = "SELECT * FROM items WHERE " +
            "DATEDIFF(expiration_date, CURDATE()) <= :days " +
            "AND (last_notification_sent = 0 OR last_notification_sent > :days)",
            nativeQuery = true)
    List<Item> findItemsNeedingNotification(@Param("days") int days);

    // VUE Notification Board
    @Query(value = "SELECT * FROM items WHERE " +
                   "DATEDIFF(expiration_date, CURDATE()) BETWEEN :minDays AND :maxDays",
                    nativeQuery = true)
    List<Item> findByExpirationWindow(@Param("minDays") int minDays, @Param("maxDays") int maxDays);
}

