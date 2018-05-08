package com.resume.service.impl;

import com.resume.service.BContactService;
import com.resume.domain.BContact;
import com.resume.repository.BContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing BContact.
 */
@Service
@Transactional
public class BContactServiceImpl implements BContactService{

    private final Logger log = LoggerFactory.getLogger(BContactServiceImpl.class);

    private final BContactRepository bContactRepository;

    public BContactServiceImpl(BContactRepository bContactRepository) {
        this.bContactRepository = bContactRepository;
    }

    /**
     * Save a bContact.
     *
     * @param bContact the entity to save
     * @return the persisted entity
     */
    @Override
    public BContact save(BContact bContact) {
        log.debug("Request to save BContact : {}", bContact);
        return bContactRepository.save(bContact);
    }

    /**
     *  Get all the bContacts.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BContact> findAll() {
        log.debug("Request to get all BContacts");
        return bContactRepository.findAll();
    }

    /**
     *  Get one bContact by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BContact findOne(Long id) {
        log.debug("Request to get BContact : {}", id);
        return bContactRepository.findOne(id);
    }

    /**
     *  Delete the  bContact by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BContact : {}", id);
        bContactRepository.delete(id);
    }
}
