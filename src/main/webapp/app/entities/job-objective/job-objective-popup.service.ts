import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { JobObjective } from './job-objective.model';
import { JobObjectiveService } from './job-objective.service';

@Injectable()
export class JobObjectivePopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private jobObjectiveService: JobObjectiveService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.jobObjectiveService.find(id).subscribe((jobObjective) => {
                jobObjective.createTime = this.datePipe
                    .transform(jobObjective.createTime, 'yyyy-MM-ddThh:mm');
                jobObjective.updateTime = this.datePipe
                    .transform(jobObjective.updateTime, 'yyyy-MM-ddThh:mm');
                this.jobObjectiveModalRef(component, jobObjective);
            });
        } else {
            return this.jobObjectiveModalRef(component, new JobObjective());
        }
    }

    jobObjectiveModalRef(component: Component, jobObjective: JobObjective): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.jobObjective = jobObjective;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
