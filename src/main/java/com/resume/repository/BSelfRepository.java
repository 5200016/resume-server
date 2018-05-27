package com.resume.repository;

import com.resume.domain.BSelf;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BSelf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BSelfRepository extends JpaRepository<BSelf,Long> {
    /**
     * 通过用户名查询自我评价
     */
    @Query("select bs from BSelf bs where bs.username = ?1 and bs.isActive = true")
    BSelf findByUsername(String username);
}
