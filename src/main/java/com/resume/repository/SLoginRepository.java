package com.resume.repository;

import com.resume.domain.SLogin;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the SLogin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SLoginRepository extends JpaRepository<SLogin,Long> {
    /**
     * 验证用户名
     */
    Optional<SLogin> findOneWithAuthoritiesByUsername(String username);

    /**
     * 通过用户名查询用户信息
     */
    @Query("select login from SLogin login where login.username = ?1 and login.isActive = true")
    SLogin findOneByUsername(String username);
}
