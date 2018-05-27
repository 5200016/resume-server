package com.resume.service;

import com.resume.domain.BSelf;
import java.util.List;

/**
 * Service Interface for managing BSelf.
 */
public interface BSelfService {

    /**
     * Save a bSelf.
     *
     * @param bSelf the entity to save
     * @return the persisted entity
     */
    BSelf save(BSelf bSelf);

    /**
     *  Get all the bSelves.
     *
     *  @return the list of entities
     */
    List<BSelf> findAll();

    /**
     *  Get the "id" bSelf.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BSelf findOne(Long id);

    /**
     *  Delete the "id" bSelf.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
