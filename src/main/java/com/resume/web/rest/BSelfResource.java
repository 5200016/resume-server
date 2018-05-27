package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.BSelf;
import com.resume.service.BSelfService;
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
 * REST controller for managing BSelf.
 */
@RestController
@RequestMapping("/api")
public class BSelfResource {

    private final Logger log = LoggerFactory.getLogger(BSelfResource.class);

    private static final String ENTITY_NAME = "bSelf";

    private final BSelfService bSelfService;

    public BSelfResource(BSelfService bSelfService) {
        this.bSelfService = bSelfService;
    }

    /**
     * POST  /b-selves : Create a new bSelf.
     *
     * @param bSelf the bSelf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bSelf, or with status 400 (Bad Request) if the bSelf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/b-selves")
    @Timed
    public ResponseEntity<BSelf> createBSelf(@RequestBody BSelf bSelf) throws URISyntaxException {
        log.debug("REST request to save BSelf : {}", bSelf);
        if (bSelf.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bSelf cannot already have an ID")).body(null);
        }
        BSelf result = bSelfService.save(bSelf);
        return ResponseEntity.created(new URI("/api/b-selves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /b-selves : Updates an existing bSelf.
     *
     * @param bSelf the bSelf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bSelf,
     * or with status 400 (Bad Request) if the bSelf is not valid,
     * or with status 500 (Internal Server Error) if the bSelf couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/b-selves")
    @Timed
    public ResponseEntity<BSelf> updateBSelf(@RequestBody BSelf bSelf) throws URISyntaxException {
        log.debug("REST request to update BSelf : {}", bSelf);
        if (bSelf.getId() == null) {
            return createBSelf(bSelf);
        }
        BSelf result = bSelfService.save(bSelf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bSelf.getId().toString()))
            .body(result);
    }

    /**
     * GET  /b-selves : get all the bSelves.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bSelves in body
     */
    @GetMapping("/b-selves")
    @Timed
    public List<BSelf> getAllBSelves() {
        log.debug("REST request to get all BSelves");
        return bSelfService.findAll();
    }

    /**
     * GET  /b-selves/:id : get the "id" bSelf.
     *
     * @param id the id of the bSelf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bSelf, or with status 404 (Not Found)
     */
    @GetMapping("/b-selves/{id}")
    @Timed
    public ResponseEntity<BSelf> getBSelf(@PathVariable Long id) {
        log.debug("REST request to get BSelf : {}", id);
        BSelf bSelf = bSelfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bSelf));
    }

    /**
     * DELETE  /b-selves/:id : delete the "id" bSelf.
     *
     * @param id the id of the bSelf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/b-selves/{id}")
    @Timed
    public ResponseEntity<Void> deleteBSelf(@PathVariable Long id) {
        log.debug("REST request to delete BSelf : {}", id);
        bSelfService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
