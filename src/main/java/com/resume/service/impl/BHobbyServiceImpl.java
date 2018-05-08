package com.resume.service.impl;

import com.resume.service.BHobbyService;
import com.resume.domain.BHobby;
import com.resume.repository.BHobbyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing BHobby.
 */
@Service
@Transactional
public class BHobbyServiceImpl implements BHobbyService{

    private final Logger log = LoggerFactory.getLogger(BHobbyServiceImpl.class);

    private final BHobbyRepository bHobbyRepository;

    public BHobbyServiceImpl(BHobbyRepository bHobbyRepository) {
        this.bHobbyRepository = bHobbyRepository;
    }

    /**
     * Save a bHobby.
     *
     * @param bHobby the entity to save
     * @return the persisted entity
     */
    @Override
    public BHobby save(BHobby bHobby) {
        log.debug("Request to save BHobby : {}", bHobby);
        return bHobbyRepository.save(bHobby);
    }

    /**
     *  Get all the bHobbies.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BHobby> findAll() {
        log.debug("Request to get all BHobbies");
        return bHobbyRepository.findAll();
    }

    /**
     *  Get one bHobby by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BHobby findOne(Long id) {
        log.debug("Request to get BHobby : {}", id);
        return bHobbyRepository.findOne(id);
    }

    /**
     *  Delete the  bHobby by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BHobby : {}", id);
        bHobbyRepository.delete(id);
    }
}
