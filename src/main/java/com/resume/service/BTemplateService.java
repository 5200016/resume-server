package com.resume.service;

import com.resume.domain.BTemplate;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service Interface for managing BTemplate.
 */
public interface BTemplateService {

    /**
     * Save a bTemplate.
     *
     * @param bTemplate the entity to save
     * @return the persisted entity
     */
    BTemplate save(BTemplate bTemplate);

    /**
     *  Get all the bTemplates.
     *
     *  @return the list of entities
     */
    List<BTemplate> findAll();

    /**
     *  Get the "id" bTemplate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BTemplate findOne(Long id);

    /**
     *  Delete the "id" bTemplate.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * 根据分类id查询相关模板（分页）
     */
    Page<BTemplate> findTemplate(Long classifyId, String name, Integer pageNum, Integer pageSize);
}
