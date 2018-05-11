package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.BTemplateClassify;
import com.resume.service.BTemplateClassifyService;
import com.resume.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BTemplateClassify.
 */
@RestController
@RequestMapping("/ap")
public class BTemplateClassifyResource {

    private final Logger log = LoggerFactory.getLogger(BTemplateClassifyResource.class);

    private static final String ENTITY_NAME = "bTemplateClassify";

    private final BTemplateClassifyService bTemplateClassifyService;

    public BTemplateClassifyResource(BTemplateClassifyService bTemplateClassifyService) {
        this.bTemplateClassifyService = bTemplateClassifyService;
    }

    /**
     * POST  /b-template-classifies : Create a new bTemplateClassify.
     *
     * @param bTemplateClassify the bTemplateClassify to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bTemplateClassify, or with status 400 (Bad Request) if the bTemplateClassify has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/b-template-classifies")
    @Timed
    public ResponseEntity<BTemplateClassify> createBTemplateClassify(@RequestBody BTemplateClassify bTemplateClassify) throws URISyntaxException {
        log.debug("REST request to save BTemplateClassify : {}", bTemplateClassify);
        if (bTemplateClassify.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bTemplateClassify cannot already have an ID")).body(null);
        }
        BTemplateClassify result = bTemplateClassifyService.save(bTemplateClassify);
        return ResponseEntity.created(new URI("/api/b-template-classifies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /b-template-classifies : Updates an existing bTemplateClassify.
     *
     * @param bTemplateClassify the bTemplateClassify to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bTemplateClassify,
     * or with status 400 (Bad Request) if the bTemplateClassify is not valid,
     * or with status 500 (Internal Server Error) if the bTemplateClassify couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/b-template-classifies")
    @Timed
    public ResponseEntity<BTemplateClassify> updateBTemplateClassify(@RequestBody BTemplateClassify bTemplateClassify) throws URISyntaxException {
        log.debug("REST request to update BTemplateClassify : {}", bTemplateClassify);
        if (bTemplateClassify.getId() == null) {
            return createBTemplateClassify(bTemplateClassify);
        }
        BTemplateClassify result = bTemplateClassifyService.save(bTemplateClassify);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bTemplateClassify.getId().toString()))
            .body(result);
    }

    /**
     * GET  /b-template-classifies : get all the bTemplateClassifies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bTemplateClassifies in body
     */
    @GetMapping("/b-template-classifies")
    @Timed
    public List<BTemplateClassify> getAllBTemplateClassifies() {
        log.debug("REST request to get all BTemplateClassifies");
        return bTemplateClassifyService.findAll();
    }

    /**
     * GET  /b-template-classifies/:id : get the "id" bTemplateClassify.
     *
     * @param id the id of the bTemplateClassify to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bTemplateClassify, or with status 404 (Not Found)
     */
    @GetMapping("/b-template-classifies/{id}")
    @Timed
    public ResponseEntity<BTemplateClassify> getBTemplateClassify(@PathVariable Long id) {
        log.debug("REST request to get BTemplateClassify : {}", id);
        BTemplateClassify bTemplateClassify = bTemplateClassifyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bTemplateClassify));
    }

    /**
     * DELETE  /b-template-classifies/:id : delete the "id" bTemplateClassify.
     *
     * @param id the id of the bTemplateClassify to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/b-template-classifies/{id}")
    @Timed
    public ResponseEntity<Void> deleteBTemplateClassify(@PathVariable Long id) {
        log.debug("REST request to delete BTemplateClassify : {}", id);
        bTemplateClassifyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
