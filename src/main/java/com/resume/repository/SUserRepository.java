package com.resume.repository;

import com.resume.domain.SUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SUserRepository extends JpaRepository<SUser,Long> {

    /**
     * 通过用户名查询用户基本信息
     */
    @Query("select user from SUser user where user.username = ?1 and user.isActive = true")
    SUser findUserByUsername(String username);
}
