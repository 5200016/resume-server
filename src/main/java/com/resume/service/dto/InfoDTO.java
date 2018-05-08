package com.resume.service.dto;

import com.resume.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User : 黄志成
 * Date : 2018/5/8
 * Desc : 用户详细信息DTO
 */

public class InfoDTO {
    private BInformation information;
    private BContact contact;
    private List<WorkProjectDTO> workProject;
    private BHonour honour;
    private BHobby hobby;
    private JobObjective jobObjective;

    public InfoDTO() {
    }

    public InfoDTO(BInformation information, BContact contact, List<WorkProjectDTO> workProject, BHonour honour, BHobby hobby, JobObjective jobObjective) {
        this.information = information;
        this.contact = contact;
        this.workProject = workProject;
        this.honour = honour;
        this.hobby = hobby;
        this.jobObjective = jobObjective;
    }

    public BInformation getInformation() {
        return information;
    }

    public void setInformation(BInformation information) {
        this.information = information;
    }

    public BContact getContact() {
        return contact;
    }

    public void setContact(BContact contact) {
        this.contact = contact;
    }

    public List<WorkProjectDTO> getWorkProject() {
        return workProject;
    }

    public void setWorkProject(List<WorkProjectDTO> workProject) {
        this.workProject = workProject;
    }

    public BHonour getHonour() {
        return honour;
    }

    public void setHonour(BHonour honour) {
        this.honour = honour;
    }

    public BHobby getHobby() {
        return hobby;
    }

    public void setHobby(BHobby hobby) {
        this.hobby = hobby;
    }

    public JobObjective getJobObjective() {
        return jobObjective;
    }

    public void setJobObjective(JobObjective jobObjective) {
        this.jobObjective = jobObjective;
    }

    @Override
    public String toString() {
        return "InfoDTO{" +
            "information=" + information +
            ", contact=" + contact +
            ", workProject=" + workProject +
            ", honour=" + honour +
            ", hobby=" + hobby +
            ", jobObjective=" + jobObjective +
            '}';
    }
}
