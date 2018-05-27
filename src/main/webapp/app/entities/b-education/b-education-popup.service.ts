import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BEducation } from './b-education.model';
import { BEducationService } from './b-education.service';

@Injectable()
export class BEducationPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bEducationService: BEducationService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bEducationService.find(id).subscribe((bEducation) => {
                bEducation.createTime = this.datePipe
                    .transform(bEducation.createTime, 'yyyy-MM-ddThh:mm');
                bEducation.updateTime = this.datePipe
                    .transform(bEducation.updateTime, 'yyyy-MM-ddThh:mm');
                this.bEducationModalRef(component, bEducation);
            });
        } else {
            return this.bEducationModalRef(component, new BEducation());
        }
    }

    bEducationModalRef(component: Component, bEducation: BEducation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bEducation = bEducation;
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
