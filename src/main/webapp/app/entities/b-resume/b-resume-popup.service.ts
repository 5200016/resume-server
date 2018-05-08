import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BResume } from './b-resume.model';
import { BResumeService } from './b-resume.service';

@Injectable()
export class BResumePopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bResumeService: BResumeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bResumeService.find(id).subscribe((bResume) => {
                bResume.createTime = this.datePipe
                    .transform(bResume.createTime, 'yyyy-MM-ddThh:mm');
                bResume.updateTime = this.datePipe
                    .transform(bResume.updateTime, 'yyyy-MM-ddThh:mm');
                this.bResumeModalRef(component, bResume);
            });
        } else {
            return this.bResumeModalRef(component, new BResume());
        }
    }

    bResumeModalRef(component: Component, bResume: BResume): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bResume = bResume;
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
