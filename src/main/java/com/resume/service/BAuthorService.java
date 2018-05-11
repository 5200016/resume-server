package com.resume.service;

import com.resume.domain.BAuthor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service Interface for managing BAuthor.
 */
public interface BAuthorService {

    /**
     * Save a bAuthor.
     *
     * @param bAuthor the entity to save
     * @return the persisted entity
     */
    BAuthor save(BAuthor bAuthor);

    /**
     *  Get all the bAuthors.
     *
     *  @return the list of entities
     */
    List<BAuthor> findAll();

    /**
     *  Get the "id" bAuthor.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BAuthor findOne(Long id);

    /**
     *  Delete the "id" bAuthor.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * 查询代写作者信息（并根据代写量，好评度排序  分页）
     */
    Page<BAuthor> findAuthor(Integer pageNum, Integer pageSize);
}
