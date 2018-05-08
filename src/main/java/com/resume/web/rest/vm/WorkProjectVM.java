package com.resume.web.rest.vm;

import com.resume.domain.BWork;
import com.resume.domain.BWorkProject;

import java.util.ArrayList;
import java.util.List;

/**
 * User : 黄志成
 * Date : 2018/5/8
 * Desc : 用户工作项目VM
 */

public class WorkProjectVM {
    private BWork work;
    private List<BWorkProject> workProjects = new ArrayList<>();

    public WorkProjectVM() {
    }

    public WorkProjectVM(BWork work, List<BWorkProject> workProjects) {
        this.work = work;
        this.workProjects = workProjects;
    }

    public BWork getWork() {
        return work;
    }

    public void setWork(BWork work) {
        this.work = work;
    }

    public List<BWorkProject> getWorkProjects() {
        return workProjects;
    }

    public void setWorkProjects(List<BWorkProject> workProjects) {
        this.workProjects = workProjects;
    }

    @Override
    public String toString() {
        return "WorkProjectVM{" +
            "work=" + work +
            ", workProjects=" + workProjects +
            '}';
    }
}
