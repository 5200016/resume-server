import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BSelf } from './b-self.model';
import { BSelfService } from './b-self.service';

@Injectable()
export class BSelfPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bSelfService: BSelfService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bSelfService.find(id).subscribe((bSelf) => {
                bSelf.createTime = this.datePipe
                    .transform(bSelf.createTime, 'yyyy-MM-ddThh:mm');
                bSelf.updateTime = this.datePipe
                    .transform(bSelf.updateTime, 'yyyy-MM-ddThh:mm');
                this.bSelfModalRef(component, bSelf);
            });
        } else {
            return this.bSelfModalRef(component, new BSelf());
        }
    }

    bSelfModalRef(component: Component, bSelf: BSelf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bSelf = bSelf;
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
