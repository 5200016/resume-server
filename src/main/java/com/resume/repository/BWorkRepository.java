package com.resume.repository;

import com.resume.domain.BWork;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the BWork entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BWorkRepository extends JpaRepository<BWork,Long> {
    /**
     * 通过用户名查询用户工作
     */
    @Query("select bw from BWork bw where bw.username = ?1 and bw.isActive = true")
    List<BWork> findByUsername(String username);
}
