package com.cams.inventory.management.repository.order;

import com.cams.inventory.management.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing OrderEntity persistence.
 * Extends JpaRepository to provide CRUD operations and query methods.
 */
@Repository("orderRepositoryV1")
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
}
