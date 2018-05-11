package com.resume.service.impl;

import com.resume.domain.*;
import com.resume.repository.*;
import com.resume.service.BInformationService;
import com.resume.service.dto.InfoDTO;
import com.resume.service.dto.WorkProjectDTO;
import com.resume.web.rest.util.DateUtil;
import com.resume.web.rest.util.TypeUtils;
import com.resume.web.rest.vm.WorkProjectVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing BInformation.
 */
@Service
@Transactional
public class BInformationServiceImpl implements BInformationService{

    private final Logger log = LoggerFactory.getLogger(BInformationServiceImpl.class);

    private final BInformationRepository bInformationRepository;
    private final BContactRepository contactRepository;
    private final BWorkRepository workRepository;
    private final BWorkProjectRepository workProjectRepository;
    private final BHonourRepository honourRepository;
    private final BHobbyRepository hobbyRepository;
    private final JobObjectiveRepository jobObjectiveRepository;

    public BInformationServiceImpl(BInformationRepository bInformationRepository, BContactRepository contactRepository, BWorkRepository workRepository, BWorkProjectRepository workProjectRepository, BHonourRepository honourRepository, BHobbyRepository hobbyRepository, JobObjectiveRepository jobObjectiveRepository) {
        this.bInformationRepository = bInformationRepository;
        this.contactRepository = contactRepository;
        this.workRepository = workRepository;
        this.workProjectRepository = workProjectRepository;
        this.honourRepository = honourRepository;
        this.hobbyRepository = hobbyRepository;
        this.jobObjectiveRepository = jobObjectiveRepository;
    }

    /**
     * Save a bInformation.
     *
     * @param bInformation the entity to save
     * @return the persisted entity
     */
    @Override
    public BInformation save(BInformation bInformation) {
        log.debug("Request to save BInformation : {}", bInformation);
        return bInformationRepository.save(bInformation);
    }

    /**
     *  Get all the bInformations.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BInformation> findAll() {
        log.debug("Request to get all BInformations");
        return bInformationRepository.findAll();
    }

    /**
     *  Get one bInformation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BInformation findOne(Long id) {
        log.debug("Request to get BInformation : {}", id);
        return bInformationRepository.findOne(id);
    }

    /**
     *  Delete the  bInformation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BInformation : {}", id);
        bInformationRepository.delete(id);
    }

    /**
     * 通过用户账号查询用户详细信息
     */
    @Override
    public InfoDTO findUserInformation(String username) {
        InfoDTO infoDTO = new InfoDTO();
        //个人详细信息
        BInformation information = bInformationRepository.findByUsername(username);
        if(!TypeUtils.isEmpty(information)){
            infoDTO.setInformation(information);
        }
        //个人联系方式
        BContact contact = contactRepository.findByUsername(username);
        if(!TypeUtils.isEmpty(contact)){
            infoDTO.setContact(contact);
        }
        //个人工作经历
        List<BWork> works = workRepository.findByUsername(username);
        List<WorkProjectDTO> workProjectDTOS = new ArrayList<>();
        if(!TypeUtils.isEmpty(works)){
            for ( BWork work : works ){
                List<BWorkProject> workProjects = workProjectRepository.findByWorkId(work.getId());
                WorkProjectDTO workProjectDTO = new WorkProjectDTO(work,workProjects);
                workProjectDTOS.add(workProjectDTO);
            }
            infoDTO.setWorkProject(workProjectDTOS);
        }
        //个人荣誉奖项
        BHonour honour = honourRepository.findByUsername(username);
        if(!TypeUtils.isEmpty(honour)){
            infoDTO.setHonour(honour);
        }
        //个人兴趣特长
        BHobby hobby = hobbyRepository.findByUsername(username);
        if(!TypeUtils.isEmpty(hobby)){
            infoDTO.setHobby(hobby);
        }
        //个人求职意向
        JobObjective jobObjective = jobObjectiveRepository.findByUsername(username);
        if(!TypeUtils.isEmpty(jobObjective)){
            infoDTO.setJobObjective(jobObjective);
        }
        return infoDTO;
    }

    /**
     * 新增用户工作经历
     */
    @Override
    public void insertWorkExperience(List<WorkProjectVM> workProjects) {
        if(!TypeUtils.isEmpty(workProjects)){
            for(WorkProjectVM workProjectVM : workProjects){
                workProjectVM.getWork().setIsActive(true);
                workProjectVM.getWork().setCreateTime(DateUtil.getZoneDateTime());
                workProjectVM.getWork().setUpdateTime(DateUtil.getZoneDateTime());
                BWork work = workRepository.save(workProjectVM.getWork());
                if(TypeUtils.isEmpty(workProjectVM.getWorkProjects())){
                    for(BWorkProject workProject : workProjectVM.getWorkProjects()){
                        workProject.setBWork(work);
                        workProject.setIsActive(true);
                        workProject.setCreateTime(DateUtil.getZoneDateTime());
                        workProject.setUpdateTime(DateUtil.getZoneDateTime());
                        workProjectRepository.save(workProject);
                    }
                }
            }

        }
    }
}
