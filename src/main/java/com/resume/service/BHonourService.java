package com.resume.service;

import com.resume.domain.BHonour;
import java.util.List;

/**
 * Service Interface for managing BHonour.
 */
public interface BHonourService {

    /**
     * Save a bHonour.
     *
     * @param bHonour the entity to save
     * @return the persisted entity
     */
    BHonour save(BHonour bHonour);

    /**
     *  Get all the bHonours.
     *
     *  @return the list of entities
     */
    List<BHonour> findAll();

    /**
     *  Get the "id" bHonour.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BHonour findOne(Long id);

    /**
     *  Delete the "id" bHonour.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
