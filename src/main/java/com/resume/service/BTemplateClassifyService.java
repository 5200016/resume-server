package com.resume.service;

import com.resume.domain.BTemplateClassify;
import java.util.List;

/**
 * Service Interface for managing BTemplateClassify.
 */
public interface BTemplateClassifyService {

    /**
     * Save a bTemplateClassify.
     *
     * @param bTemplateClassify the entity to save
     * @return the persisted entity
     */
    BTemplateClassify save(BTemplateClassify bTemplateClassify);

    /**
     *  Get all the bTemplateClassifies.
     *
     *  @return the list of entities
     */
    List<BTemplateClassify> findAll();

    /**
     *  Get the "id" bTemplateClassify.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BTemplateClassify findOne(Long id);

    /**
     *  Delete the "id" bTemplateClassify.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
