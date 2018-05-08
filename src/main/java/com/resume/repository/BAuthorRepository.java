package com.resume.repository;

import com.resume.domain.BAuthor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BAuthor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BAuthorRepository extends JpaRepository<BAuthor,Long> {
    
}
