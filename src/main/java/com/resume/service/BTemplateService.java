package com.resume.service;

import com.resume.domain.BTemplate;
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
}
