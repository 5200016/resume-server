import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BWork } from './b-work.model';
import { BWorkService } from './b-work.service';

@Injectable()
export class BWorkPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bWorkService: BWorkService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bWorkService.find(id).subscribe((bWork) => {
                bWork.createTime = this.datePipe
                    .transform(bWork.createTime, 'yyyy-MM-ddThh:mm');
                bWork.updateTime = this.datePipe
                    .transform(bWork.updateTime, 'yyyy-MM-ddThh:mm');
                this.bWorkModalRef(component, bWork);
            });
        } else {
            return this.bWorkModalRef(component, new BWork());
        }
    }

    bWorkModalRef(component: Component, bWork: BWork): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bWork = bWork;
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
