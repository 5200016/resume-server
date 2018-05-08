import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BContact } from './b-contact.model';
import { BContactService } from './b-contact.service';

@Injectable()
export class BContactPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bContactService: BContactService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bContactService.find(id).subscribe((bContact) => {
                bContact.createTime = this.datePipe
                    .transform(bContact.createTime, 'yyyy-MM-ddThh:mm');
                bContact.updateTime = this.datePipe
                    .transform(bContact.updateTime, 'yyyy-MM-ddThh:mm');
                this.bContactModalRef(component, bContact);
            });
        } else {
            return this.bContactModalRef(component, new BContact());
        }
    }

    bContactModalRef(component: Component, bContact: BContact): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bContact = bContact;
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
