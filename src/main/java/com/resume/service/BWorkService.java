package com.resume.service;

import com.resume.domain.BWork;
import java.util.List;

/**
 * Service Interface for managing BWork.
 */
public interface BWorkService {

    /**
     * Save a bWork.
     *
     * @param bWork the entity to save
     * @return the persisted entity
     */
    BWork save(BWork bWork);

    /**
     *  Get all the bWorks.
     *
     *  @return the list of entities
     */
    List<BWork> findAll();

    /**
     *  Get the "id" bWork.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BWork findOne(Long id);

    /**
     *  Delete the "id" bWork.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
