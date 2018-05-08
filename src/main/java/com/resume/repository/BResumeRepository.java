package com.resume.repository;

import com.resume.domain.BResume;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BResume entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BResumeRepository extends JpaRepository<BResume,Long> {
    
}
