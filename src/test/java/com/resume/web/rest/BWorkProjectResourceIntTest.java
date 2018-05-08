package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.BWorkProject;
import com.resume.repository.BWorkProjectRepository;
import com.resume.service.BWorkProjectService;
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
 * Test class for the BWorkProjectResource REST controller.
 *
 * @see BWorkProjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class BWorkProjectResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSIBLE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSIBLE = "BBBBBBBBBB";

    private static final String DEFAULT_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_END_TIME = "AAAAAAAAAA";
    private static final String UPDATED_END_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BWorkProjectRepository bWorkProjectRepository;

    @Autowired
    private BWorkProjectService bWorkProjectService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBWorkProjectMockMvc;

    private BWorkProject bWorkProject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BWorkProjectResource bWorkProjectResource = new BWorkProjectResource(bWorkProjectService);
        this.restBWorkProjectMockMvc = MockMvcBuilders.standaloneSetup(bWorkProjectResource)
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
    public static BWorkProject createEntity(EntityManager em) {
        BWorkProject bWorkProject = new BWorkProject()
            .name(DEFAULT_NAME)
            .responsible(DEFAULT_RESPONSIBLE)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .extra(DEFAULT_EXTRA)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return bWorkProject;
    }

    @Before
    public void initTest() {
        bWorkProject = createEntity(em);
    }

    @Test
    @Transactional
    public void createBWorkProject() throws Exception {
        int databaseSizeBeforeCreate = bWorkProjectRepository.findAll().size();

        // Create the BWorkProject
        restBWorkProjectMockMvc.perform(post("/api/b-work-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bWorkProject)))
            .andExpect(status().isCreated());

        // Validate the BWorkProject in the database
        List<BWorkProject> bWorkProjectList = bWorkProjectRepository.findAll();
        assertThat(bWorkProjectList).hasSize(databaseSizeBeforeCreate + 1);
        BWorkProject testBWorkProject = bWorkProjectList.get(bWorkProjectList.size() - 1);
        assertThat(testBWorkProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBWorkProject.getResponsible()).isEqualTo(DEFAULT_RESPONSIBLE);
        assertThat(testBWorkProject.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testBWorkProject.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testBWorkProject.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testBWorkProject.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testBWorkProject.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testBWorkProject.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createBWorkProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bWorkProjectRepository.findAll().size();

        // Create the BWorkProject with an existing ID
        bWorkProject.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBWorkProjectMockMvc.perform(post("/api/b-work-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bWorkProject)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BWorkProject> bWorkProjectList = bWorkProjectRepository.findAll();
        assertThat(bWorkProjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBWorkProjects() throws Exception {
        // Initialize the database
        bWorkProjectRepository.saveAndFlush(bWorkProject);

        // Get all the bWorkProjectList
        restBWorkProjectMockMvc.perform(get("/api/b-work-projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bWorkProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].responsible").value(hasItem(DEFAULT_RESPONSIBLE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getBWorkProject() throws Exception {
        // Initialize the database
        bWorkProjectRepository.saveAndFlush(bWorkProject);

        // Get the bWorkProject
        restBWorkProjectMockMvc.perform(get("/api/b-work-projects/{id}", bWorkProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bWorkProject.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.responsible").value(DEFAULT_RESPONSIBLE.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBWorkProject() throws Exception {
        // Get the bWorkProject
        restBWorkProjectMockMvc.perform(get("/api/b-work-projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBWorkProject() throws Exception {
        // Initialize the database
        bWorkProjectService.save(bWorkProject);

        int databaseSizeBeforeUpdate = bWorkProjectRepository.findAll().size();

        // Update the bWorkProject
        BWorkProject updatedBWorkProject = bWorkProjectRepository.findOne(bWorkProject.getId());
        updatedBWorkProject
            .name(UPDATED_NAME)
            .responsible(UPDATED_RESPONSIBLE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .extra(UPDATED_EXTRA)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restBWorkProjectMockMvc.perform(put("/api/b-work-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBWorkProject)))
            .andExpect(status().isOk());

        // Validate the BWorkProject in the database
        List<BWorkProject> bWorkProjectList = bWorkProjectRepository.findAll();
        assertThat(bWorkProjectList).hasSize(databaseSizeBeforeUpdate);
        BWorkProject testBWorkProject = bWorkProjectList.get(bWorkProjectList.size() - 1);
        assertThat(testBWorkProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBWorkProject.getResponsible()).isEqualTo(UPDATED_RESPONSIBLE);
        assertThat(testBWorkProject.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testBWorkProject.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testBWorkProject.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testBWorkProject.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testBWorkProject.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testBWorkProject.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBWorkProject() throws Exception {
        int databaseSizeBeforeUpdate = bWorkProjectRepository.findAll().size();

        // Create the BWorkProject

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBWorkProjectMockMvc.perform(put("/api/b-work-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bWorkProject)))
            .andExpect(status().isCreated());

        // Validate the BWorkProject in the database
        List<BWorkProject> bWorkProjectList = bWorkProjectRepository.findAll();
        assertThat(bWorkProjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBWorkProject() throws Exception {
        // Initialize the database
        bWorkProjectService.save(bWorkProject);

        int databaseSizeBeforeDelete = bWorkProjectRepository.findAll().size();

        // Get the bWorkProject
        restBWorkProjectMockMvc.perform(delete("/api/b-work-projects/{id}", bWorkProject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BWorkProject> bWorkProjectList = bWorkProjectRepository.findAll();
        assertThat(bWorkProjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BWorkProject.class);
        BWorkProject bWorkProject1 = new BWorkProject();
        bWorkProject1.setId(1L);
        BWorkProject bWorkProject2 = new BWorkProject();
        bWorkProject2.setId(bWorkProject1.getId());
        assertThat(bWorkProject1).isEqualTo(bWorkProject2);
        bWorkProject2.setId(2L);
        assertThat(bWorkProject1).isNotEqualTo(bWorkProject2);
        bWorkProject1.setId(null);
        assertThat(bWorkProject1).isNotEqualTo(bWorkProject2);
    }
}
