import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BTemplateClassify } from './b-template-classify.model';
import { BTemplateClassifyService } from './b-template-classify.service';

@Injectable()
export class BTemplateClassifyPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bTemplateClassifyService: BTemplateClassifyService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bTemplateClassifyService.find(id).subscribe((bTemplateClassify) => {
                bTemplateClassify.createTime = this.datePipe
                    .transform(bTemplateClassify.createTime, 'yyyy-MM-ddThh:mm');
                bTemplateClassify.updateTime = this.datePipe
                    .transform(bTemplateClassify.updateTime, 'yyyy-MM-ddThh:mm');
                this.bTemplateClassifyModalRef(component, bTemplateClassify);
            });
        } else {
            return this.bTemplateClassifyModalRef(component, new BTemplateClassify());
        }
    }

    bTemplateClassifyModalRef(component: Component, bTemplateClassify: BTemplateClassify): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bTemplateClassify = bTemplateClassify;
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
