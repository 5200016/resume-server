package com.resume.repository;

import com.resume.domain.BTemplateClassify;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BTemplateClassify entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BTemplateClassifyRepository extends JpaRepository<BTemplateClassify,Long> {
    
}
