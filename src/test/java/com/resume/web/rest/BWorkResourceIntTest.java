package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.BWork;
import com.resume.repository.BWorkRepository;
import com.resume.service.BWorkService;
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
 * Test class for the BWorkResource REST controller.
 *
 * @see BWorkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class BWorkResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_TIME = "AAAAAAAAAA";
    private static final String UPDATED_WORK_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

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
    private BWorkRepository bWorkRepository;

    @Autowired
    private BWorkService bWorkService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBWorkMockMvc;

    private BWork bWork;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BWorkResource bWorkResource = new BWorkResource(bWorkService);
        this.restBWorkMockMvc = MockMvcBuilders.standaloneSetup(bWorkResource)
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
    public static BWork createEntity(EntityManager em) {
        BWork bWork = new BWork()
            .username(DEFAULT_USERNAME)
            .workTime(DEFAULT_WORK_TIME)
            .companyName(DEFAULT_COMPANY_NAME)
            .jobTitle(DEFAULT_JOB_TITLE)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .extra(DEFAULT_EXTRA)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return bWork;
    }

    @Before
    public void initTest() {
        bWork = createEntity(em);
    }

    @Test
    @Transactional
    public void createBWork() throws Exception {
        int databaseSizeBeforeCreate = bWorkRepository.findAll().size();

        // Create the BWork
        restBWorkMockMvc.perform(post("/api/b-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bWork)))
            .andExpect(status().isCreated());

        // Validate the BWork in the database
        List<BWork> bWorkList = bWorkRepository.findAll();
        assertThat(bWorkList).hasSize(databaseSizeBeforeCreate + 1);
        BWork testBWork = bWorkList.get(bWorkList.size() - 1);
        assertThat(testBWork.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testBWork.getWorkTime()).isEqualTo(DEFAULT_WORK_TIME);
        assertThat(testBWork.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testBWork.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testBWork.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testBWork.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testBWork.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testBWork.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testBWork.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testBWork.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createBWorkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bWorkRepository.findAll().size();

        // Create the BWork with an existing ID
        bWork.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBWorkMockMvc.perform(post("/api/b-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bWork)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BWork> bWorkList = bWorkRepository.findAll();
        assertThat(bWorkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBWorks() throws Exception {
        // Initialize the database
        bWorkRepository.saveAndFlush(bWork);

        // Get all the bWorkList
        restBWorkMockMvc.perform(get("/api/b-works?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bWork.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].workTime").value(hasItem(DEFAULT_WORK_TIME.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getBWork() throws Exception {
        // Initialize the database
        bWorkRepository.saveAndFlush(bWork);

        // Get the bWork
        restBWorkMockMvc.perform(get("/api/b-works/{id}", bWork.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bWork.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.workTime").value(DEFAULT_WORK_TIME.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBWork() throws Exception {
        // Get the bWork
        restBWorkMockMvc.perform(get("/api/b-works/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBWork() throws Exception {
        // Initialize the database
        bWorkService.save(bWork);

        int databaseSizeBeforeUpdate = bWorkRepository.findAll().size();

        // Update the bWork
        BWork updatedBWork = bWorkRepository.findOne(bWork.getId());
        updatedBWork
            .username(UPDATED_USERNAME)
            .workTime(UPDATED_WORK_TIME)
            .companyName(UPDATED_COMPANY_NAME)
            .jobTitle(UPDATED_JOB_TITLE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .extra(UPDATED_EXTRA)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restBWorkMockMvc.perform(put("/api/b-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBWork)))
            .andExpect(status().isOk());

        // Validate the BWork in the database
        List<BWork> bWorkList = bWorkRepository.findAll();
        assertThat(bWorkList).hasSize(databaseSizeBeforeUpdate);
        BWork testBWork = bWorkList.get(bWorkList.size() - 1);
        assertThat(testBWork.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testBWork.getWorkTime()).isEqualTo(UPDATED_WORK_TIME);
        assertThat(testBWork.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testBWork.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testBWork.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testBWork.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testBWork.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testBWork.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testBWork.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testBWork.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBWork() throws Exception {
        int databaseSizeBeforeUpdate = bWorkRepository.findAll().size();

        // Create the BWork

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBWorkMockMvc.perform(put("/api/b-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bWork)))
            .andExpect(status().isCreated());

        // Validate the BWork in the database
        List<BWork> bWorkList = bWorkRepository.findAll();
        assertThat(bWorkList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBWork() throws Exception {
        // Initialize the database
        bWorkService.save(bWork);

        int databaseSizeBeforeDelete = bWorkRepository.findAll().size();

        // Get the bWork
        restBWorkMockMvc.perform(delete("/api/b-works/{id}", bWork.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BWork> bWorkList = bWorkRepository.findAll();
        assertThat(bWorkList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BWork.class);
        BWork bWork1 = new BWork();
        bWork1.setId(1L);
        BWork bWork2 = new BWork();
        bWork2.setId(bWork1.getId());
        assertThat(bWork1).isEqualTo(bWork2);
        bWork2.setId(2L);
        assertThat(bWork1).isNotEqualTo(bWork2);
        bWork1.setId(null);
        assertThat(bWork1).isNotEqualTo(bWork2);
    }
}
