package com.ecommerce.Ecommerce.V1.repository;

import com.ecommerce.Ecommerce.V1.model.PasswordRecovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecovery, UUID> {
}
