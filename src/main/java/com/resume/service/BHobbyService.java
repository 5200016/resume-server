package com.resume.service;

import com.resume.domain.BHobby;
import java.util.List;

/**
 * Service Interface for managing BHobby.
 */
public interface BHobbyService {

    /**
     * Save a bHobby.
     *
     * @param bHobby the entity to save
     * @return the persisted entity
     */
    BHobby save(BHobby bHobby);

    /**
     *  Get all the bHobbies.
     *
     *  @return the list of entities
     */
    List<BHobby> findAll();

    /**
     *  Get the "id" bHobby.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BHobby findOne(Long id);

    /**
     *  Delete the "id" bHobby.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
