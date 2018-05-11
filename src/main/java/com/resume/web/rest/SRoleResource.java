package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.SRole;
import com.resume.service.SRoleService;
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
 * REST controller for managing SRole.
 */
@RestController
@RequestMapping("/ap")
public class SRoleResource {

    private final Logger log = LoggerFactory.getLogger(SRoleResource.class);

    private static final String ENTITY_NAME = "sRole";

    private final SRoleService sRoleService;

    public SRoleResource(SRoleService sRoleService) {
        this.sRoleService = sRoleService;
    }

    /**
     * POST  /s-roles : Create a new sRole.
     *
     * @param sRole the sRole to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sRole, or with status 400 (Bad Request) if the sRole has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/s-roles")
    @Timed
    public ResponseEntity<SRole> createSRole(@RequestBody SRole sRole) throws URISyntaxException {
        log.debug("REST request to save SRole : {}", sRole);
        if (sRole.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sRole cannot already have an ID")).body(null);
        }
        SRole result = sRoleService.save(sRole);
        return ResponseEntity.created(new URI("/api/s-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /s-roles : Updates an existing sRole.
     *
     * @param sRole the sRole to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sRole,
     * or with status 400 (Bad Request) if the sRole is not valid,
     * or with status 500 (Internal Server Error) if the sRole couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/s-roles")
    @Timed
    public ResponseEntity<SRole> updateSRole(@RequestBody SRole sRole) throws URISyntaxException {
        log.debug("REST request to update SRole : {}", sRole);
        if (sRole.getId() == null) {
            return createSRole(sRole);
        }
        SRole result = sRoleService.save(sRole);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sRole.getId().toString()))
            .body(result);
    }

    /**
     * GET  /s-roles : get all the sRoles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sRoles in body
     */
    @GetMapping("/s-roles")
    @Timed
    public List<SRole> getAllSRoles() {
        log.debug("REST request to get all SRoles");
        return sRoleService.findAll();
    }

    /**
     * GET  /s-roles/:id : get the "id" sRole.
     *
     * @param id the id of the sRole to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sRole, or with status 404 (Not Found)
     */
    @GetMapping("/s-roles/{id}")
    @Timed
    public ResponseEntity<SRole> getSRole(@PathVariable Long id) {
        log.debug("REST request to get SRole : {}", id);
        SRole sRole = sRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sRole));
    }

    /**
     * DELETE  /s-roles/:id : delete the "id" sRole.
     *
     * @param id the id of the sRole to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/s-roles/{id}")
    @Timed
    public ResponseEntity<Void> deleteSRole(@PathVariable Long id) {
        log.debug("REST request to delete SRole : {}", id);
        sRoleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
