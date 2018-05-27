package com.resume.repository;

import com.resume.domain.BWorkProject;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the BWorkProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BWorkProjectRepository extends JpaRepository<BWorkProject,Long> {
    /**
     * 通过用户名查询用户工作项目
     */
    @Query("select bwp from BWorkProject bwp where bwp.username = ?1 and bwp.isActive = true")
    List<BWorkProject> findByUsername(String username);
}
