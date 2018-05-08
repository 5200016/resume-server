import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BHobby } from './b-hobby.model';
import { BHobbyService } from './b-hobby.service';

@Injectable()
export class BHobbyPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bHobbyService: BHobbyService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bHobbyService.find(id).subscribe((bHobby) => {
                bHobby.createTime = this.datePipe
                    .transform(bHobby.createTime, 'yyyy-MM-ddThh:mm');
                bHobby.updateTime = this.datePipe
                    .transform(bHobby.updateTime, 'yyyy-MM-ddThh:mm');
                this.bHobbyModalRef(component, bHobby);
            });
        } else {
            return this.bHobbyModalRef(component, new BHobby());
        }
    }

    bHobbyModalRef(component: Component, bHobby: BHobby): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bHobby = bHobby;
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
