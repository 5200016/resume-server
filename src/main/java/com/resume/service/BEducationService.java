package com.resume.service;

import com.resume.domain.BEducation;
import java.util.List;

/**
 * Service Interface for managing BEducation.
 */
public interface BEducationService {

    /**
     * Save a bEducation.
     *
     * @param bEducation the entity to save
     * @return the persisted entity
     */
    BEducation save(BEducation bEducation);

    /**
     *  Get all the bEducations.
     *
     *  @return the list of entities
     */
    List<BEducation> findAll();

    /**
     *  Get the "id" bEducation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BEducation findOne(Long id);

    /**
     *  Delete the "id" bEducation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
