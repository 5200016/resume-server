package com.resume.repository;

import com.resume.domain.BTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the BTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BTemplateRepository extends JpaRepository<BTemplate,Long> {
    
    @Query("select distinct b_template from BTemplate b_template left join fetch b_template.classifies")
    List<BTemplate> findAllWithEagerRelationships();

    @Query("select b_template from BTemplate b_template left join fetch b_template.classifies where b_template.id =:id")
    BTemplate findOneWithEagerRelationships(@Param("id") Long id);
    
}
