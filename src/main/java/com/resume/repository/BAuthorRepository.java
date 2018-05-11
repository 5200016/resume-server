package com.resume.repository;

import com.resume.domain.BAuthor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BAuthor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BAuthorRepository extends JpaRepository<BAuthor,Long> {
    @Query("select author from BAuthor author" +
        " where author.isActive = true" +
        " order by author.likeCount desc , author.writeCount desc")
    Page<BAuthor> findAuthor(Pageable pageable);
}
