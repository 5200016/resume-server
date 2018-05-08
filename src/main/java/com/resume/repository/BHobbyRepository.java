package com.resume.repository;

import com.resume.domain.BHobby;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BHobby entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BHobbyRepository extends JpaRepository<BHobby,Long> {
    /**
     * 通过用户名查询用户兴趣特长
     */
    @Query("select bhb from BHobby bhb where bhb.username = ?1 and bhb.isActive = true")
    BHobby findByUsername(String username);
}
