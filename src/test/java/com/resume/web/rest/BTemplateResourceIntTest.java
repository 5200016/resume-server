package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.BTemplate;
import com.resume.repository.BTemplateRepository;
import com.resume.service.BTemplateService;
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
 * Test class for the BTemplateResource REST controller.
 *
 * @see BTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class BTemplateResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CLICK_COUNT = 1L;
    private static final Long UPDATED_CLICK_COUNT = 2L;

    private static final String DEFAULT_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_PRICE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final String DEFAULT_EXTRA = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BTemplateRepository bTemplateRepository;

    @Autowired
    private BTemplateService bTemplateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBTemplateMockMvc;

    private BTemplate bTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BTemplateResource bTemplateResource = new BTemplateResource(bTemplateService);
        this.restBTemplateMockMvc = MockMvcBuilders.standaloneSetup(bTemplateResource)
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
    public static BTemplate createEntity(EntityManager em) {
        BTemplate bTemplate = new BTemplate()
            .url(DEFAULT_URL)
            .description(DEFAULT_DESCRIPTION)
            .clickCount(DEFAULT_CLICK_COUNT)
            .price(DEFAULT_PRICE)
            .type(DEFAULT_TYPE)
            .extra(DEFAULT_EXTRA)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return bTemplate;
    }

    @Before
    public void initTest() {
        bTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createBTemplate() throws Exception {
        int databaseSizeBeforeCreate = bTemplateRepository.findAll().size();

        // Create the BTemplate
        restBTemplateMockMvc.perform(post("/api/b-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bTemplate)))
            .andExpect(status().isCreated());

        // Validate the BTemplate in the database
        List<BTemplate> bTemplateList = bTemplateRepository.findAll();
        assertThat(bTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        BTemplate testBTemplate = bTemplateList.get(bTemplateList.size() - 1);
        assertThat(testBTemplate.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testBTemplate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBTemplate.getClickCount()).isEqualTo(DEFAULT_CLICK_COUNT);
        assertThat(testBTemplate.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBTemplate.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBTemplate.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testBTemplate.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testBTemplate.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testBTemplate.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createBTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bTemplateRepository.findAll().size();

        // Create the BTemplate with an existing ID
        bTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBTemplateMockMvc.perform(post("/api/b-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BTemplate> bTemplateList = bTemplateRepository.findAll();
        assertThat(bTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBTemplates() throws Exception {
        // Initialize the database
        bTemplateRepository.saveAndFlush(bTemplate);

        // Get all the bTemplateList
        restBTemplateMockMvc.perform(get("/api/b-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].clickCount").value(hasItem(DEFAULT_CLICK_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getBTemplate() throws Exception {
        // Initialize the database
        bTemplateRepository.saveAndFlush(bTemplate);

        // Get the bTemplate
        restBTemplateMockMvc.perform(get("/api/b-templates/{id}", bTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bTemplate.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.clickCount").value(DEFAULT_CLICK_COUNT.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBTemplate() throws Exception {
        // Get the bTemplate
        restBTemplateMockMvc.perform(get("/api/b-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBTemplate() throws Exception {
        // Initialize the database
        bTemplateService.save(bTemplate);

        int databaseSizeBeforeUpdate = bTemplateRepository.findAll().size();

        // Update the bTemplate
        BTemplate updatedBTemplate = bTemplateRepository.findOne(bTemplate.getId());
        updatedBTemplate
            .url(UPDATED_URL)
            .description(UPDATED_DESCRIPTION)
            .clickCount(UPDATED_CLICK_COUNT)
            .price(UPDATED_PRICE)
            .type(UPDATED_TYPE)
            .extra(UPDATED_EXTRA)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restBTemplateMockMvc.perform(put("/api/b-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBTemplate)))
            .andExpect(status().isOk());

        // Validate the BTemplate in the database
        List<BTemplate> bTemplateList = bTemplateRepository.findAll();
        assertThat(bTemplateList).hasSize(databaseSizeBeforeUpdate);
        BTemplate testBTemplate = bTemplateList.get(bTemplateList.size() - 1);
        assertThat(testBTemplate.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testBTemplate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBTemplate.getClickCount()).isEqualTo(UPDATED_CLICK_COUNT);
        assertThat(testBTemplate.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBTemplate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBTemplate.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testBTemplate.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testBTemplate.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testBTemplate.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBTemplate() throws Exception {
        int databaseSizeBeforeUpdate = bTemplateRepository.findAll().size();

        // Create the BTemplate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBTemplateMockMvc.perform(put("/api/b-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bTemplate)))
            .andExpect(status().isCreated());

        // Validate the BTemplate in the database
        List<BTemplate> bTemplateList = bTemplateRepository.findAll();
        assertThat(bTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBTemplate() throws Exception {
        // Initialize the database
        bTemplateService.save(bTemplate);

        int databaseSizeBeforeDelete = bTemplateRepository.findAll().size();

        // Get the bTemplate
        restBTemplateMockMvc.perform(delete("/api/b-templates/{id}", bTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BTemplate> bTemplateList = bTemplateRepository.findAll();
        assertThat(bTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BTemplate.class);
        BTemplate bTemplate1 = new BTemplate();
        bTemplate1.setId(1L);
        BTemplate bTemplate2 = new BTemplate();
        bTemplate2.setId(bTemplate1.getId());
        assertThat(bTemplate1).isEqualTo(bTemplate2);
        bTemplate2.setId(2L);
        assertThat(bTemplate1).isNotEqualTo(bTemplate2);
        bTemplate1.setId(null);
        assertThat(bTemplate1).isNotEqualTo(bTemplate2);
    }
}
