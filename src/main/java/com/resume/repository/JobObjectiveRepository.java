package com.resume.repository;

import com.resume.domain.JobObjective;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the JobObjective entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobObjectiveRepository extends JpaRepository<JobObjective,Long> {
    /**
     * 通过用户名查询用户求职意向
     */
    @Query("select jo from JobObjective jo where jo.username = ?1 and jo.isActive = true")
    JobObjective findByUsername(String username);
}
