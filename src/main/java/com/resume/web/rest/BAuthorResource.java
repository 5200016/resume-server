package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.BAuthor;
import com.resume.service.BAuthorService;
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
 * REST controller for managing BAuthor.
 */
@RestController
@RequestMapping("/ap")
public class BAuthorResource {

    private final Logger log = LoggerFactory.getLogger(BAuthorResource.class);

    private static final String ENTITY_NAME = "bAuthor";

    private final BAuthorService bAuthorService;

    public BAuthorResource(BAuthorService bAuthorService) {
        this.bAuthorService = bAuthorService;
    }

    /**
     * POST  /b-authors : Create a new bAuthor.
     *
     * @param bAuthor the bAuthor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bAuthor, or with status 400 (Bad Request) if the bAuthor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/b-authors")
    @Timed
    public ResponseEntity<BAuthor> createBAuthor(@RequestBody BAuthor bAuthor) throws URISyntaxException {
        log.debug("REST request to save BAuthor : {}", bAuthor);
        if (bAuthor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bAuthor cannot already have an ID")).body(null);
        }
        BAuthor result = bAuthorService.save(bAuthor);
        return ResponseEntity.created(new URI("/api/b-authors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /b-authors : Updates an existing bAuthor.
     *
     * @param bAuthor the bAuthor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bAuthor,
     * or with status 400 (Bad Request) if the bAuthor is not valid,
     * or with status 500 (Internal Server Error) if the bAuthor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/b-authors")
    @Timed
    public ResponseEntity<BAuthor> updateBAuthor(@RequestBody BAuthor bAuthor) throws URISyntaxException {
        log.debug("REST request to update BAuthor : {}", bAuthor);
        if (bAuthor.getId() == null) {
            return createBAuthor(bAuthor);
        }
        BAuthor result = bAuthorService.save(bAuthor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bAuthor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /b-authors : get all the bAuthors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bAuthors in body
     */
    @GetMapping("/b-authors")
    @Timed
    public List<BAuthor> getAllBAuthors() {
        log.debug("REST request to get all BAuthors");
        return bAuthorService.findAll();
    }

    /**
     * GET  /b-authors/:id : get the "id" bAuthor.
     *
     * @param id the id of the bAuthor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bAuthor, or with status 404 (Not Found)
     */
    @GetMapping("/b-authors/{id}")
    @Timed
    public ResponseEntity<BAuthor> getBAuthor(@PathVariable Long id) {
        log.debug("REST request to get BAuthor : {}", id);
        BAuthor bAuthor = bAuthorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bAuthor));
    }

    /**
     * DELETE  /b-authors/:id : delete the "id" bAuthor.
     *
     * @param id the id of the bAuthor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/b-authors/{id}")
    @Timed
    public ResponseEntity<Void> deleteBAuthor(@PathVariable Long id) {
        log.debug("REST request to delete BAuthor : {}", id);
        bAuthorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
