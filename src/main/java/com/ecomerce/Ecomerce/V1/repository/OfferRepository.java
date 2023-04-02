package com.ecomerce.Ecomerce.V1.repository;

import com.ecomerce.Ecomerce.V1.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {
}
