package com.resume.repository;

import com.resume.domain.BContact;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BContactRepository extends JpaRepository<BContact,Long> {
    /**
     * 通过用户名查询用户联系方式
     */
    @Query("select bc from BContact bc where bc.username = ?1 and bc.isActive = true")
    BContact findByUsername(String username);
}
