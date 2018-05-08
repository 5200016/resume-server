package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.BInformation;
import com.resume.repository.BInformationRepository;
import com.resume.service.BInformationService;
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
 * Test class for the BInformationResource REST controller.
 *
 * @see BInformationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class BInformationResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SEX = "AAAAAAAAAA";
    private static final String UPDATED_SEX = "BBBBBBBBBB";

    private static final String DEFAULT_BIRTHDAY = "AAAAAAAAAA";
    private static final String UPDATED_BIRTHDAY = "BBBBBBBBBB";

    private static final String DEFAULT_EDUCATION = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALITY = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALITY = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_TIME = "AAAAAAAAAA";
    private static final String UPDATED_WORK_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_POLITICS_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_POLITICS_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_MARRIAGE = "AAAAAAAAAA";
    private static final String UPDATED_MARRIAGE = "BBBBBBBBBB";

    private static final String DEFAULT_SKILL = "AAAAAAAAAA";
    private static final String UPDATED_SKILL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BInformationRepository bInformationRepository;

    @Autowired
    private BInformationService bInformationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBInformationMockMvc;

    private BInformation bInformation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BInformationResource bInformationResource = new BInformationResource(bInformationService);
        this.restBInformationMockMvc = MockMvcBuilders.standaloneSetup(bInformationResource)
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
    public static BInformation createEntity(EntityManager em) {
        BInformation bInformation = new BInformation()
            .username(DEFAULT_USERNAME)
            .name(DEFAULT_NAME)
            .sex(DEFAULT_SEX)
            .birthday(DEFAULT_BIRTHDAY)
            .education(DEFAULT_EDUCATION)
            .speciality(DEFAULT_SPECIALITY)
            .workTime(DEFAULT_WORK_TIME)
            .politicsStatus(DEFAULT_POLITICS_STATUS)
            .marriage(DEFAULT_MARRIAGE)
            .skill(DEFAULT_SKILL)
            .address(DEFAULT_ADDRESS)
            .introduction(DEFAULT_INTRODUCTION)
            .extra(DEFAULT_EXTRA)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return bInformation;
    }

    @Before
    public void initTest() {
        bInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createBInformation() throws Exception {
        int databaseSizeBeforeCreate = bInformationRepository.findAll().size();

        // Create the BInformation
        restBInformationMockMvc.perform(post("/api/b-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bInformation)))
            .andExpect(status().isCreated());

        // Validate the BInformation in the database
        List<BInformation> bInformationList = bInformationRepository.findAll();
        assertThat(bInformationList).hasSize(databaseSizeBeforeCreate + 1);
        BInformation testBInformation = bInformationList.get(bInformationList.size() - 1);
        assertThat(testBInformation.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testBInformation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBInformation.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testBInformation.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testBInformation.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testBInformation.getSpeciality()).isEqualTo(DEFAULT_SPECIALITY);
        assertThat(testBInformation.getWorkTime()).isEqualTo(DEFAULT_WORK_TIME);
        assertThat(testBInformation.getPoliticsStatus()).isEqualTo(DEFAULT_POLITICS_STATUS);
        assertThat(testBInformation.getMarriage()).isEqualTo(DEFAULT_MARRIAGE);
        assertThat(testBInformation.getSkill()).isEqualTo(DEFAULT_SKILL);
        assertThat(testBInformation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testBInformation.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);
        assertThat(testBInformation.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testBInformation.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testBInformation.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testBInformation.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createBInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bInformationRepository.findAll().size();

        // Create the BInformation with an existing ID
        bInformation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBInformationMockMvc.perform(post("/api/b-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bInformation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BInformation> bInformationList = bInformationRepository.findAll();
        assertThat(bInformationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBInformations() throws Exception {
        // Initialize the database
        bInformationRepository.saveAndFlush(bInformation);

        // Get all the bInformationList
        restBInformationMockMvc.perform(get("/api/b-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION.toString())))
            .andExpect(jsonPath("$.[*].speciality").value(hasItem(DEFAULT_SPECIALITY.toString())))
            .andExpect(jsonPath("$.[*].workTime").value(hasItem(DEFAULT_WORK_TIME.toString())))
            .andExpect(jsonPath("$.[*].politicsStatus").value(hasItem(DEFAULT_POLITICS_STATUS.toString())))
            .andExpect(jsonPath("$.[*].marriage").value(hasItem(DEFAULT_MARRIAGE.toString())))
            .andExpect(jsonPath("$.[*].skill").value(hasItem(DEFAULT_SKILL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getBInformation() throws Exception {
        // Initialize the database
        bInformationRepository.saveAndFlush(bInformation);

        // Get the bInformation
        restBInformationMockMvc.perform(get("/api/b-informations/{id}", bInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bInformation.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.education").value(DEFAULT_EDUCATION.toString()))
            .andExpect(jsonPath("$.speciality").value(DEFAULT_SPECIALITY.toString()))
            .andExpect(jsonPath("$.workTime").value(DEFAULT_WORK_TIME.toString()))
            .andExpect(jsonPath("$.politicsStatus").value(DEFAULT_POLITICS_STATUS.toString()))
            .andExpect(jsonPath("$.marriage").value(DEFAULT_MARRIAGE.toString()))
            .andExpect(jsonPath("$.skill").value(DEFAULT_SKILL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBInformation() throws Exception {
        // Get the bInformation
        restBInformationMockMvc.perform(get("/api/b-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBInformation() throws Exception {
        // Initialize the database
        bInformationService.save(bInformation);

        int databaseSizeBeforeUpdate = bInformationRepository.findAll().size();

        // Update the bInformation
        BInformation updatedBInformation = bInformationRepository.findOne(bInformation.getId());
        updatedBInformation
            .username(UPDATED_USERNAME)
            .name(UPDATED_NAME)
            .sex(UPDATED_SEX)
            .birthday(UPDATED_BIRTHDAY)
            .education(UPDATED_EDUCATION)
            .speciality(UPDATED_SPECIALITY)
            .workTime(UPDATED_WORK_TIME)
            .politicsStatus(UPDATED_POLITICS_STATUS)
            .marriage(UPDATED_MARRIAGE)
            .skill(UPDATED_SKILL)
            .address(UPDATED_ADDRESS)
            .introduction(UPDATED_INTRODUCTION)
            .extra(UPDATED_EXTRA)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restBInformationMockMvc.perform(put("/api/b-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBInformation)))
            .andExpect(status().isOk());

        // Validate the BInformation in the database
        List<BInformation> bInformationList = bInformationRepository.findAll();
        assertThat(bInformationList).hasSize(databaseSizeBeforeUpdate);
        BInformation testBInformation = bInformationList.get(bInformationList.size() - 1);
        assertThat(testBInformation.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testBInformation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBInformation.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testBInformation.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testBInformation.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testBInformation.getSpeciality()).isEqualTo(UPDATED_SPECIALITY);
        assertThat(testBInformation.getWorkTime()).isEqualTo(UPDATED_WORK_TIME);
        assertThat(testBInformation.getPoliticsStatus()).isEqualTo(UPDATED_POLITICS_STATUS);
        assertThat(testBInformation.getMarriage()).isEqualTo(UPDATED_MARRIAGE);
        assertThat(testBInformation.getSkill()).isEqualTo(UPDATED_SKILL);
        assertThat(testBInformation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBInformation.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);
        assertThat(testBInformation.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testBInformation.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testBInformation.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testBInformation.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBInformation() throws Exception {
        int databaseSizeBeforeUpdate = bInformationRepository.findAll().size();

        // Create the BInformation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBInformationMockMvc.perform(put("/api/b-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bInformation)))
            .andExpect(status().isCreated());

        // Validate the BInformation in the database
        List<BInformation> bInformationList = bInformationRepository.findAll();
        assertThat(bInformationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBInformation() throws Exception {
        // Initialize the database
        bInformationService.save(bInformation);

        int databaseSizeBeforeDelete = bInformationRepository.findAll().size();

        // Get the bInformation
        restBInformationMockMvc.perform(delete("/api/b-informations/{id}", bInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BInformation> bInformationList = bInformationRepository.findAll();
        assertThat(bInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BInformation.class);
        BInformation bInformation1 = new BInformation();
        bInformation1.setId(1L);
        BInformation bInformation2 = new BInformation();
        bInformation2.setId(bInformation1.getId());
        assertThat(bInformation1).isEqualTo(bInformation2);
        bInformation2.setId(2L);
        assertThat(bInformation1).isNotEqualTo(bInformation2);
        bInformation1.setId(null);
        assertThat(bInformation1).isNotEqualTo(bInformation2);
    }
}
