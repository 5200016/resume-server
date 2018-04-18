package com.resume.repository;

import com.resume.domain.SRole;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SRoleRepository extends JpaRepository<SRole,Long> {
    
}
