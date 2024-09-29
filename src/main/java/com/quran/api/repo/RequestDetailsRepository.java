package com.quran.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quran.api.entity.RequestDetails;

public interface RequestDetailsRepository extends JpaRepository<RequestDetails, Long> {
}

