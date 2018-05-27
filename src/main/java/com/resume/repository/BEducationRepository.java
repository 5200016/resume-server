package com.resume.repository;

import com.resume.domain.BEducation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BEducation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BEducationRepository extends JpaRepository<BEducation,Long> {
    /**
     * 通过用户名查询教育背景
     */
    @Query("select be from BEducation be where be.username = ?1 and be.isActive = true")
    BEducation findByUsername(String username);
}
