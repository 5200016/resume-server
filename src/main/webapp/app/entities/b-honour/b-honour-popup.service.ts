import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BHonour } from './b-honour.model';
import { BHonourService } from './b-honour.service';

@Injectable()
export class BHonourPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bHonourService: BHonourService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bHonourService.find(id).subscribe((bHonour) => {
                bHonour.createTime = this.datePipe
                    .transform(bHonour.createTime, 'yyyy-MM-ddThh:mm');
                bHonour.updateTime = this.datePipe
                    .transform(bHonour.updateTime, 'yyyy-MM-ddThh:mm');
                this.bHonourModalRef(component, bHonour);
            });
        } else {
            return this.bHonourModalRef(component, new BHonour());
        }
    }

    bHonourModalRef(component: Component, bHonour: BHonour): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bHonour = bHonour;
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
