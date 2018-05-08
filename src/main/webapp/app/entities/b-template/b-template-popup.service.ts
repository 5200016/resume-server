import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BTemplate } from './b-template.model';
import { BTemplateService } from './b-template.service';

@Injectable()
export class BTemplatePopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bTemplateService: BTemplateService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bTemplateService.find(id).subscribe((bTemplate) => {
                bTemplate.createTime = this.datePipe
                    .transform(bTemplate.createTime, 'yyyy-MM-ddThh:mm');
                bTemplate.updateTime = this.datePipe
                    .transform(bTemplate.updateTime, 'yyyy-MM-ddThh:mm');
                this.bTemplateModalRef(component, bTemplate);
            });
        } else {
            return this.bTemplateModalRef(component, new BTemplate());
        }
    }

    bTemplateModalRef(component: Component, bTemplate: BTemplate): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bTemplate = bTemplate;
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
