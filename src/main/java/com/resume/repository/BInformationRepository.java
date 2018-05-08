package com.resume.repository;

import com.resume.domain.BInformation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BInformationRepository extends JpaRepository<BInformation,Long> {

    /**
     * 通过用户名查询用户详细信息
     */
    @Query("select info from BInformation info where info.username = ?1 and info.isActive = true")
    BInformation findByUsername(String username);
}
