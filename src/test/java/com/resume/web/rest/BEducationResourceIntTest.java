package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.BEducation;
import com.resume.repository.BEducationRepository;
import com.resume.service.BEducationService;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the BEducationResource REST controller.
 *
 * @see BEducationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class BEducationResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENTER_TIME = "AAAAAAAAAA";
    private static final String UPDATED_ENTER_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_STOP_TIME = "AAAAAAAAAA";
    private static final String UPDATED_STOP_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOL = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALITY = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALITY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BEducationRepository bEducationRepository;

    @Autowired
    private BEducationService bEducationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBEducationMockMvc;

    private BEducation bEducation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BEducationResource bEducationResource = new BEducationResource(bEducationService);
        this.restBEducationMockMvc = MockMvcBuilders.standaloneSetup(bEducationResource)
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
    public static BEducation createEntity(EntityManager em) {
        BEducation bEducation = new BEducation()
            .username(DEFAULT_USERNAME)
            .enterTime(DEFAULT_ENTER_TIME)
            .stopTime(DEFAULT_STOP_TIME)
            .school(DEFAULT_SCHOOL)
            .speciality(DEFAULT_SPECIALITY)
            .description(DEFAULT_DESCRIPTION)
            .extra(DEFAULT_EXTRA)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return bEducation;
    }

    @Before
    public void initTest() {
        bEducation = createEntity(em);
    }

    @Test
    @Transactional
    public void createBEducation() throws Exception {
        int databaseSizeBeforeCreate = bEducationRepository.findAll().size();

        // Create the BEducation
        restBEducationMockMvc.perform(post("/api/b-educations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bEducation)))
            .andExpect(status().isCreated());

        // Validate the BEducation in the database
        List<BEducation> bEducationList = bEducationRepository.findAll();
        assertThat(bEducationList).hasSize(databaseSizeBeforeCreate + 1);
        BEducation testBEducation = bEducationList.get(bEducationList.size() - 1);
        assertThat(testBEducation.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testBEducation.getEnterTime()).isEqualTo(DEFAULT_ENTER_TIME);
        assertThat(testBEducation.getStopTime()).isEqualTo(DEFAULT_STOP_TIME);
        assertThat(testBEducation.getSchool()).isEqualTo(DEFAULT_SCHOOL);
        assertThat(testBEducation.getSpeciality()).isEqualTo(DEFAULT_SPECIALITY);
        assertThat(testBEducation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBEducation.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testBEducation.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testBEducation.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testBEducation.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createBEducationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bEducationRepository.findAll().size();

        // Create the BEducation with an existing ID
        bEducation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBEducationMockMvc.perform(post("/api/b-educations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bEducation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BEducation> bEducationList = bEducationRepository.findAll();
        assertThat(bEducationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBEducations() throws Exception {
        // Initialize the database
        bEducationRepository.saveAndFlush(bEducation);

        // Get all the bEducationList
        restBEducationMockMvc.perform(get("/api/b-educations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bEducation.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].enterTime").value(hasItem(DEFAULT_ENTER_TIME.toString())))
            .andExpect(jsonPath("$.[*].stopTime").value(hasItem(DEFAULT_STOP_TIME.toString())))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL.toString())))
            .andExpect(jsonPath("$.[*].speciality").value(hasItem(DEFAULT_SPECIALITY.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getBEducation() throws Exception {
        // Initialize the database
        bEducationRepository.saveAndFlush(bEducation);

        // Get the bEducation
        restBEducationMockMvc.perform(get("/api/b-educations/{id}", bEducation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bEducation.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.enterTime").value(DEFAULT_ENTER_TIME.toString()))
            .andExpect(jsonPath("$.stopTime").value(DEFAULT_STOP_TIME.toString()))
            .andExpect(jsonPath("$.school").value(DEFAULT_SCHOOL.toString()))
            .andExpect(jsonPath("$.speciality").value(DEFAULT_SPECIALITY.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBEducation() throws Exception {
        // Get the bEducation
        restBEducationMockMvc.perform(get("/api/b-educations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBEducation() throws Exception {
        // Initialize the database
        bEducationService.save(bEducation);

        int databaseSizeBeforeUpdate = bEducationRepository.findAll().size();

        // Update the bEducation
        BEducation updatedBEducation = bEducationRepository.findOne(bEducation.getId());
        updatedBEducation
            .username(UPDATED_USERNAME)
            .enterTime(UPDATED_ENTER_TIME)
            .stopTime(UPDATED_STOP_TIME)
            .school(UPDATED_SCHOOL)
            .speciality(UPDATED_SPECIALITY)
            .description(UPDATED_DESCRIPTION)
            .extra(UPDATED_EXTRA)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restBEducationMockMvc.perform(put("/api/b-educations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBEducation)))
            .andExpect(status().isOk());

        // Validate the BEducation in the database
        List<BEducation> bEducationList = bEducationRepository.findAll();
        assertThat(bEducationList).hasSize(databaseSizeBeforeUpdate);
        BEducation testBEducation = bEducationList.get(bEducationList.size() - 1);
        assertThat(testBEducation.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testBEducation.getEnterTime()).isEqualTo(UPDATED_ENTER_TIME);
        assertThat(testBEducation.getStopTime()).isEqualTo(UPDATED_STOP_TIME);
        assertThat(testBEducation.getSchool()).isEqualTo(UPDATED_SCHOOL);
        assertThat(testBEducation.getSpeciality()).isEqualTo(UPDATED_SPECIALITY);
        assertThat(testBEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBEducation.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testBEducation.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testBEducation.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testBEducation.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBEducation() throws Exception {
        int databaseSizeBeforeUpdate = bEducationRepository.findAll().size();

        // Create the BEducation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBEducationMockMvc.perform(put("/api/b-educations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bEducation)))
            .andExpect(status().isCreated());

        // Validate the BEducation in the database
        List<BEducation> bEducationList = bEducationRepository.findAll();
        assertThat(bEducationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBEducation() throws Exception {
        // Initialize the database
        bEducationService.save(bEducation);

        int databaseSizeBeforeDelete = bEducationRepository.findAll().size();

        // Get the bEducation
        restBEducationMockMvc.perform(delete("/api/b-educations/{id}", bEducation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BEducation> bEducationList = bEducationRepository.findAll();
        assertThat(bEducationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BEducation.class);
        BEducation bEducation1 = new BEducation();
        bEducation1.setId(1L);
        BEducation bEducation2 = new BEducation();
        bEducation2.setId(bEducation1.getId());
        assertThat(bEducation1).isEqualTo(bEducation2);
        bEducation2.setId(2L);
        assertThat(bEducation1).isNotEqualTo(bEducation2);
        bEducation1.setId(null);
        assertThat(bEducation1).isNotEqualTo(bEducation2);
    }
}
