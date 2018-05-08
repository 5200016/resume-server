package com.resume.repository;

import com.resume.domain.BHonour;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BHonour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BHonourRepository extends JpaRepository<BHonour,Long> {
    /**
     * 通过用户名查询用户荣誉奖项
     */
    @Query("select bh from BHonour bh where bh.username = ?1 and bh.isActive = true")
    BHonour findByUsername(String username);
}
