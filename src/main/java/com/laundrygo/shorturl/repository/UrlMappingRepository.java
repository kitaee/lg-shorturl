package com.laundrygo.shorturl.repository;

import com.laundrygo.shorturl.domain.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByOriUrl(String oriUrl);
}
