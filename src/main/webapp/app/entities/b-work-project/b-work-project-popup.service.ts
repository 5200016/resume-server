import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BWorkProject } from './b-work-project.model';
import { BWorkProjectService } from './b-work-project.service';

@Injectable()
export class BWorkProjectPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bWorkProjectService: BWorkProjectService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bWorkProjectService.find(id).subscribe((bWorkProject) => {
                bWorkProject.createTime = this.datePipe
                    .transform(bWorkProject.createTime, 'yyyy-MM-ddThh:mm');
                bWorkProject.updateTime = this.datePipe
                    .transform(bWorkProject.updateTime, 'yyyy-MM-ddThh:mm');
                this.bWorkProjectModalRef(component, bWorkProject);
            });
        } else {
            return this.bWorkProjectModalRef(component, new BWorkProject());
        }
    }

    bWorkProjectModalRef(component: Component, bWorkProject: BWorkProject): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bWorkProject = bWorkProject;
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
