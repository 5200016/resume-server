import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BInformation } from './b-information.model';
import { BInformationService } from './b-information.service';

@Injectable()
export class BInformationPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bInformationService: BInformationService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bInformationService.find(id).subscribe((bInformation) => {
                bInformation.createTime = this.datePipe
                    .transform(bInformation.createTime, 'yyyy-MM-ddThh:mm');
                bInformation.updateTime = this.datePipe
                    .transform(bInformation.updateTime, 'yyyy-MM-ddThh:mm');
                this.bInformationModalRef(component, bInformation);
            });
        } else {
            return this.bInformationModalRef(component, new BInformation());
        }
    }

    bInformationModalRef(component: Component, bInformation: BInformation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bInformation = bInformation;
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
