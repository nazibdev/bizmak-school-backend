package com.commons.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commons.admin.models.Subscriptions;

public interface SubcriptionRepository extends JpaRepository<Subscriptions, Long> {

}
