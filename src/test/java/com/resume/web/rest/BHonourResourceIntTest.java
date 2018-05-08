package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.BHonour;
import com.resume.repository.BHonourRepository;
import com.resume.service.BHonourService;
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
 * Test class for the BHonourResource REST controller.
 *
 * @see BHonourResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class BHonourResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

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
    private BHonourRepository bHonourRepository;

    @Autowired
    private BHonourService bHonourService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBHonourMockMvc;

    private BHonour bHonour;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BHonourResource bHonourResource = new BHonourResource(bHonourService);
        this.restBHonourMockMvc = MockMvcBuilders.standaloneSetup(bHonourResource)
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
    public static BHonour createEntity(EntityManager em) {
        BHonour bHonour = new BHonour()
            .username(DEFAULT_USERNAME)
            .description(DEFAULT_DESCRIPTION)
            .extra(DEFAULT_EXTRA)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return bHonour;
    }

    @Before
    public void initTest() {
        bHonour = createEntity(em);
    }

    @Test
    @Transactional
    public void createBHonour() throws Exception {
        int databaseSizeBeforeCreate = bHonourRepository.findAll().size();

        // Create the BHonour
        restBHonourMockMvc.perform(post("/api/b-honours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bHonour)))
            .andExpect(status().isCreated());

        // Validate the BHonour in the database
        List<BHonour> bHonourList = bHonourRepository.findAll();
        assertThat(bHonourList).hasSize(databaseSizeBeforeCreate + 1);
        BHonour testBHonour = bHonourList.get(bHonourList.size() - 1);
        assertThat(testBHonour.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testBHonour.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBHonour.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testBHonour.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testBHonour.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testBHonour.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createBHonourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bHonourRepository.findAll().size();

        // Create the BHonour with an existing ID
        bHonour.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBHonourMockMvc.perform(post("/api/b-honours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bHonour)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BHonour> bHonourList = bHonourRepository.findAll();
        assertThat(bHonourList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBHonours() throws Exception {
        // Initialize the database
        bHonourRepository.saveAndFlush(bHonour);

        // Get all the bHonourList
        restBHonourMockMvc.perform(get("/api/b-honours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bHonour.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getBHonour() throws Exception {
        // Initialize the database
        bHonourRepository.saveAndFlush(bHonour);

        // Get the bHonour
        restBHonourMockMvc.perform(get("/api/b-honours/{id}", bHonour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bHonour.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBHonour() throws Exception {
        // Get the bHonour
        restBHonourMockMvc.perform(get("/api/b-honours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBHonour() throws Exception {
        // Initialize the database
        bHonourService.save(bHonour);

        int databaseSizeBeforeUpdate = bHonourRepository.findAll().size();

        // Update the bHonour
        BHonour updatedBHonour = bHonourRepository.findOne(bHonour.getId());
        updatedBHonour
            .username(UPDATED_USERNAME)
            .description(UPDATED_DESCRIPTION)
            .extra(UPDATED_EXTRA)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restBHonourMockMvc.perform(put("/api/b-honours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBHonour)))
            .andExpect(status().isOk());

        // Validate the BHonour in the database
        List<BHonour> bHonourList = bHonourRepository.findAll();
        assertThat(bHonourList).hasSize(databaseSizeBeforeUpdate);
        BHonour testBHonour = bHonourList.get(bHonourList.size() - 1);
        assertThat(testBHonour.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testBHonour.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBHonour.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testBHonour.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testBHonour.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testBHonour.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBHonour() throws Exception {
        int databaseSizeBeforeUpdate = bHonourRepository.findAll().size();

        // Create the BHonour

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBHonourMockMvc.perform(put("/api/b-honours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bHonour)))
            .andExpect(status().isCreated());

        // Validate the BHonour in the database
        List<BHonour> bHonourList = bHonourRepository.findAll();
        assertThat(bHonourList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBHonour() throws Exception {
        // Initialize the database
        bHonourService.save(bHonour);

        int databaseSizeBeforeDelete = bHonourRepository.findAll().size();

        // Get the bHonour
        restBHonourMockMvc.perform(delete("/api/b-honours/{id}", bHonour.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BHonour> bHonourList = bHonourRepository.findAll();
        assertThat(bHonourList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BHonour.class);
        BHonour bHonour1 = new BHonour();
        bHonour1.setId(1L);
        BHonour bHonour2 = new BHonour();
        bHonour2.setId(bHonour1.getId());
        assertThat(bHonour1).isEqualTo(bHonour2);
        bHonour2.setId(2L);
        assertThat(bHonour1).isNotEqualTo(bHonour2);
        bHonour1.setId(null);
        assertThat(bHonour1).isNotEqualTo(bHonour2);
    }
}
