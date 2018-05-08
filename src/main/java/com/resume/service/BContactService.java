package com.resume.service;

import com.resume.domain.BContact;
import java.util.List;

/**
 * Service Interface for managing BContact.
 */
public interface BContactService {

    /**
     * Save a bContact.
     *
     * @param bContact the entity to save
     * @return the persisted entity
     */
    BContact save(BContact bContact);

    /**
     *  Get all the bContacts.
     *
     *  @return the list of entities
     */
    List<BContact> findAll();

    /**
     *  Get the "id" bContact.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BContact findOne(Long id);

    /**
     *  Delete the "id" bContact.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
