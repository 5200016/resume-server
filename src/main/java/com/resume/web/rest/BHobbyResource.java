package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.BHobby;
import com.resume.service.BHobbyService;
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
 * REST controller for managing BHobby.
 */
@RestController
@RequestMapping("/ap")
public class BHobbyResource {

    private final Logger log = LoggerFactory.getLogger(BHobbyResource.class);

    private static final String ENTITY_NAME = "bHobby";

    private final BHobbyService bHobbyService;

    public BHobbyResource(BHobbyService bHobbyService) {
        this.bHobbyService = bHobbyService;
    }

    /**
     * POST  /b-hobbies : Create a new bHobby.
     *
     * @param bHobby the bHobby to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bHobby, or with status 400 (Bad Request) if the bHobby has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/b-hobbies")
    @Timed
    public ResponseEntity<BHobby> createBHobby(@RequestBody BHobby bHobby) throws URISyntaxException {
        log.debug("REST request to save BHobby : {}", bHobby);
        if (bHobby.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bHobby cannot already have an ID")).body(null);
        }
        BHobby result = bHobbyService.save(bHobby);
        return ResponseEntity.created(new URI("/api/b-hobbies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /b-hobbies : Updates an existing bHobby.
     *
     * @param bHobby the bHobby to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bHobby,
     * or with status 400 (Bad Request) if the bHobby is not valid,
     * or with status 500 (Internal Server Error) if the bHobby couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/b-hobbies")
    @Timed
    public ResponseEntity<BHobby> updateBHobby(@RequestBody BHobby bHobby) throws URISyntaxException {
        log.debug("REST request to update BHobby : {}", bHobby);
        if (bHobby.getId() == null) {
            return createBHobby(bHobby);
        }
        BHobby result = bHobbyService.save(bHobby);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bHobby.getId().toString()))
            .body(result);
    }

    /**
     * GET  /b-hobbies : get all the bHobbies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bHobbies in body
     */
    @GetMapping("/b-hobbies")
    @Timed
    public List<BHobby> getAllBHobbies() {
        log.debug("REST request to get all BHobbies");
        return bHobbyService.findAll();
    }

    /**
     * GET  /b-hobbies/:id : get the "id" bHobby.
     *
     * @param id the id of the bHobby to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bHobby, or with status 404 (Not Found)
     */
    @GetMapping("/b-hobbies/{id}")
    @Timed
    public ResponseEntity<BHobby> getBHobby(@PathVariable Long id) {
        log.debug("REST request to get BHobby : {}", id);
        BHobby bHobby = bHobbyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bHobby));
    }

    /**
     * DELETE  /b-hobbies/:id : delete the "id" bHobby.
     *
     * @param id the id of the bHobby to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/b-hobbies/{id}")
    @Timed
    public ResponseEntity<Void> deleteBHobby(@PathVariable Long id) {
        log.debug("REST request to delete BHobby : {}", id);
        bHobbyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
