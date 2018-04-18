package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.SRole;
import com.resume.repository.SRoleRepository;
import com.resume.service.SRoleService;
import com.resume.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.resume.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SRoleResource REST controller.
 *
 * @see SRoleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class SRoleResourceIntTest {

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SRoleRepository sRoleRepository;

    @Autowired
    private SRoleService sRoleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSRoleMockMvc;

    private SRole sRole;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SRoleResource sRoleResource = new SRoleResource(sRoleService);
        this.restSRoleMockMvc = MockMvcBuilders.standaloneSetup(sRoleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SRole createEntity(EntityManager em) {
        SRole sRole = new SRole()
            .roleName(DEFAULT_ROLE_NAME)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return sRole;
    }

    @Before
    public void initTest() {
        sRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createSRole() throws Exception {
        int databaseSizeBeforeCreate = sRoleRepository.findAll().size();

        // Create the SRole
        restSRoleMockMvc.perform(post("/api/s-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sRole)))
            .andExpect(status().isCreated());

        // Validate the SRole in the database
        List<SRole> sRoleList = sRoleRepository.findAll();
        assertThat(sRoleList).hasSize(databaseSizeBeforeCreate + 1);
        SRole testSRole = sRoleList.get(sRoleList.size() - 1);
        assertThat(testSRole.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
        assertThat(testSRole.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testSRole.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testSRole.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createSRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sRoleRepository.findAll().size();

        // Create the SRole with an existing ID
        sRole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSRoleMockMvc.perform(post("/api/s-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sRole)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SRole> sRoleList = sRoleRepository.findAll();
        assertThat(sRoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSRoles() throws Exception {
        // Initialize the database
        sRoleRepository.saveAndFlush(sRole);

        // Get all the sRoleList
        restSRoleMockMvc.perform(get("/api/s-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getSRole() throws Exception {
        // Initialize the database
        sRoleRepository.saveAndFlush(sRole);

        // Get the sRole
        restSRoleMockMvc.perform(get("/api/s-roles/{id}", sRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sRole.getId().intValue()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingSRole() throws Exception {
        // Get the sRole
        restSRoleMockMvc.perform(get("/api/s-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSRole() throws Exception {
        // Initialize the database
        sRoleService.save(sRole);

        int databaseSizeBeforeUpdate = sRoleRepository.findAll().size();

        // Update the sRole
        SRole updatedSRole = sRoleRepository.findOne(sRole.getId());
        updatedSRole
            .roleName(UPDATED_ROLE_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restSRoleMockMvc.perform(put("/api/s-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSRole)))
            .andExpect(status().isOk());

        // Validate the SRole in the database
        List<SRole> sRoleList = sRoleRepository.findAll();
        assertThat(sRoleList).hasSize(databaseSizeBeforeUpdate);
        SRole testSRole = sRoleList.get(sRoleList.size() - 1);
        assertThat(testSRole.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
        assertThat(testSRole.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSRole.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testSRole.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSRole() throws Exception {
        int databaseSizeBeforeUpdate = sRoleRepository.findAll().size();

        // Create the SRole

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSRoleMockMvc.perform(put("/api/s-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sRole)))
            .andExpect(status().isCreated());

        // Validate the SRole in the database
        List<SRole> sRoleList = sRoleRepository.findAll();
        assertThat(sRoleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSRole() throws Exception {
        // Initialize the database
        sRoleService.save(sRole);

        int databaseSizeBeforeDelete = sRoleRepository.findAll().size();

        // Get the sRole
        restSRoleMockMvc.perform(delete("/api/s-roles/{id}", sRole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SRole> sRoleList = sRoleRepository.findAll();
        assertThat(sRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SRole.class);
        SRole sRole1 = new SRole();
        sRole1.setId(1L);
        SRole sRole2 = new SRole();
        sRole2.setId(sRole1.getId());
        assertThat(sRole1).isEqualTo(sRole2);
        sRole2.setId(2L);
        assertThat(sRole1).isNotEqualTo(sRole2);
        sRole1.setId(null);
        assertThat(sRole1).isNotEqualTo(sRole2);
    }
}
