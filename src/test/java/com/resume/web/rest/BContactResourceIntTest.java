package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.BContact;
import com.resume.repository.BContactRepository;
import com.resume.service.BContactService;
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
 * Test class for the BContactResource REST controller.
 *
 * @see BContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class BContactResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_QQ = "AAAAAAAAAA";
    private static final String UPDATED_QQ = "BBBBBBBBBB";

    private static final String DEFAULT_WECHAT = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BContactRepository bContactRepository;

    @Autowired
    private BContactService bContactService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBContactMockMvc;

    private BContact bContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BContactResource bContactResource = new BContactResource(bContactService);
        this.restBContactMockMvc = MockMvcBuilders.standaloneSetup(bContactResource)
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
    public static BContact createEntity(EntityManager em) {
        BContact bContact = new BContact()
            .username(DEFAULT_USERNAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .qq(DEFAULT_QQ)
            .wechat(DEFAULT_WECHAT)
            .extra(DEFAULT_EXTRA)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return bContact;
    }

    @Before
    public void initTest() {
        bContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createBContact() throws Exception {
        int databaseSizeBeforeCreate = bContactRepository.findAll().size();

        // Create the BContact
        restBContactMockMvc.perform(post("/api/b-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bContact)))
            .andExpect(status().isCreated());

        // Validate the BContact in the database
        List<BContact> bContactList = bContactRepository.findAll();
        assertThat(bContactList).hasSize(databaseSizeBeforeCreate + 1);
        BContact testBContact = bContactList.get(bContactList.size() - 1);
        assertThat(testBContact.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testBContact.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testBContact.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBContact.getQq()).isEqualTo(DEFAULT_QQ);
        assertThat(testBContact.getWechat()).isEqualTo(DEFAULT_WECHAT);
        assertThat(testBContact.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testBContact.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testBContact.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testBContact.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createBContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bContactRepository.findAll().size();

        // Create the BContact with an existing ID
        bContact.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBContactMockMvc.perform(post("/api/b-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bContact)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BContact> bContactList = bContactRepository.findAll();
        assertThat(bContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBContacts() throws Exception {
        // Initialize the database
        bContactRepository.saveAndFlush(bContact);

        // Get all the bContactList
        restBContactMockMvc.perform(get("/api/b-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].qq").value(hasItem(DEFAULT_QQ.toString())))
            .andExpect(jsonPath("$.[*].wechat").value(hasItem(DEFAULT_WECHAT.toString())))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getBContact() throws Exception {
        // Initialize the database
        bContactRepository.saveAndFlush(bContact);

        // Get the bContact
        restBContactMockMvc.perform(get("/api/b-contacts/{id}", bContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bContact.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.qq").value(DEFAULT_QQ.toString()))
            .andExpect(jsonPath("$.wechat").value(DEFAULT_WECHAT.toString()))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBContact() throws Exception {
        // Get the bContact
        restBContactMockMvc.perform(get("/api/b-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBContact() throws Exception {
        // Initialize the database
        bContactService.save(bContact);

        int databaseSizeBeforeUpdate = bContactRepository.findAll().size();

        // Update the bContact
        BContact updatedBContact = bContactRepository.findOne(bContact.getId());
        updatedBContact
            .username(UPDATED_USERNAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .qq(UPDATED_QQ)
            .wechat(UPDATED_WECHAT)
            .extra(UPDATED_EXTRA)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restBContactMockMvc.perform(put("/api/b-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBContact)))
            .andExpect(status().isOk());

        // Validate the BContact in the database
        List<BContact> bContactList = bContactRepository.findAll();
        assertThat(bContactList).hasSize(databaseSizeBeforeUpdate);
        BContact testBContact = bContactList.get(bContactList.size() - 1);
        assertThat(testBContact.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testBContact.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testBContact.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBContact.getQq()).isEqualTo(UPDATED_QQ);
        assertThat(testBContact.getWechat()).isEqualTo(UPDATED_WECHAT);
        assertThat(testBContact.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testBContact.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testBContact.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testBContact.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBContact() throws Exception {
        int databaseSizeBeforeUpdate = bContactRepository.findAll().size();

        // Create the BContact

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBContactMockMvc.perform(put("/api/b-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bContact)))
            .andExpect(status().isCreated());

        // Validate the BContact in the database
        List<BContact> bContactList = bContactRepository.findAll();
        assertThat(bContactList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBContact() throws Exception {
        // Initialize the database
        bContactService.save(bContact);

        int databaseSizeBeforeDelete = bContactRepository.findAll().size();

        // Get the bContact
        restBContactMockMvc.perform(delete("/api/b-contacts/{id}", bContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BContact> bContactList = bContactRepository.findAll();
        assertThat(bContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BContact.class);
        BContact bContact1 = new BContact();
        bContact1.setId(1L);
        BContact bContact2 = new BContact();
        bContact2.setId(bContact1.getId());
        assertThat(bContact1).isEqualTo(bContact2);
        bContact2.setId(2L);
        assertThat(bContact1).isNotEqualTo(bContact2);
        bContact1.setId(null);
        assertThat(bContact1).isNotEqualTo(bContact2);
    }
}
