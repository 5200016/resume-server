package com.resume.service.impl;

import com.resume.service.BAuthorService;
import com.resume.domain.BAuthor;
import com.resume.repository.BAuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing BAuthor.
 */
@Service
@Transactional
public class BAuthorServiceImpl implements BAuthorService{

    private final Logger log = LoggerFactory.getLogger(BAuthorServiceImpl.class);

    private final BAuthorRepository bAuthorRepository;

    public BAuthorServiceImpl(BAuthorRepository bAuthorRepository) {
        this.bAuthorRepository = bAuthorRepository;
    }

    /**
     * Save a bAuthor.
     *
     * @param bAuthor the entity to save
     * @return the persisted entity
     */
    @Override
    public BAuthor save(BAuthor bAuthor) {
        log.debug("Request to save BAuthor : {}", bAuthor);
        return bAuthorRepository.save(bAuthor);
    }

    /**
     *  Get all the bAuthors.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BAuthor> findAll() {
        log.debug("Request to get all BAuthors");
        return bAuthorRepository.findAll();
    }

    /**
     *  Get one bAuthor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BAuthor findOne(Long id) {
        log.debug("Request to get BAuthor : {}", id);
        return bAuthorRepository.findOne(id);
    }

    /**
     *  Delete the  bAuthor by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BAuthor : {}", id);
        bAuthorRepository.delete(id);
    }

    /**
     * 查询代写作者信息（并根据代写量，好评度排序  分页）
     */
    @Override
    public Page<BAuthor> findAuthor(Integer pageNum, Integer pageSize) {
        return bAuthorRepository.findAuthor(new PageRequest(pageNum,pageSize));
    }
}
