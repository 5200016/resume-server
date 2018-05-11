package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.SUser;
import com.resume.service.SUserService;
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
 * REST controller for managing SUser.
 */
@RestController
@RequestMapping("/ap")
public class SUserResource {

    private final Logger log = LoggerFactory.getLogger(SUserResource.class);

    private static final String ENTITY_NAME = "sUser";

    private final SUserService sUserService;

    public SUserResource(SUserService sUserService) {
        this.sUserService = sUserService;
    }

    /**
     * POST  /s-users : Create a new sUser.
     *
     * @param sUser the sUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sUser, or with status 400 (Bad Request) if the sUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/s-users")
    @Timed
    public ResponseEntity<SUser> createSUser(@RequestBody SUser sUser) throws URISyntaxException {
        log.debug("REST request to save SUser : {}", sUser);
        if (sUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sUser cannot already have an ID")).body(null);
        }
        SUser result = sUserService.save(sUser);
        return ResponseEntity.created(new URI("/api/s-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /s-users : Updates an existing sUser.
     *
     * @param sUser the sUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sUser,
     * or with status 400 (Bad Request) if the sUser is not valid,
     * or with status 500 (Internal Server Error) if the sUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/s-users")
    @Timed
    public ResponseEntity<SUser> updateSUser(@RequestBody SUser sUser) throws URISyntaxException {
        log.debug("REST request to update SUser : {}", sUser);
        if (sUser.getId() == null) {
            return createSUser(sUser);
        }
        SUser result = sUserService.save(sUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /s-users : get all the sUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sUsers in body
     */
    @GetMapping("/s-users")
    @Timed
    public List<SUser> getAllSUsers() {
        log.debug("REST request to get all SUsers");
        return sUserService.findAll();
    }

    /**
     * GET  /s-users/:id : get the "id" sUser.
     *
     * @param id the id of the sUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sUser, or with status 404 (Not Found)
     */
    @GetMapping("/s-users/{id}")
    @Timed
    public ResponseEntity<SUser> getSUser(@PathVariable Long id) {
        log.debug("REST request to get SUser : {}", id);
        SUser sUser = sUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sUser));
    }

    /**
     * DELETE  /s-users/:id : delete the "id" sUser.
     *
     * @param id the id of the sUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/s-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteSUser(@PathVariable Long id) {
        log.debug("REST request to delete SUser : {}", id);
        sUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
