package com.resume.repository;

import com.resume.domain.BTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * 根据分类id查询相关模板（分页）
     */
    @Query("select bt from BTemplate bt left join bt.classifies btc" +
        " where (0 = ?3 or btc.id = ?1)" +
        " and (0 = ?4 or bt.description like concat('%',?2,'%'))" +
        " and btc.isActive = true" +
        " and bt.isActive = true")
    Page<BTemplate> findTemplateByClassify(Long classifyId, String name, Integer classifyFlag, Integer nameFlag, Pageable pageable);
}
