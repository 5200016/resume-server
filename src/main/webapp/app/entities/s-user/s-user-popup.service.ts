import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { SUser } from './s-user.model';
import { SUserService } from './s-user.service';

@Injectable()
export class SUserPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private sUserService: SUserService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.sUserService.find(id).subscribe((sUser) => {
                sUser.createTime = this.datePipe
                    .transform(sUser.createTime, 'yyyy-MM-ddThh:mm');
                sUser.updateTime = this.datePipe
                    .transform(sUser.updateTime, 'yyyy-MM-ddThh:mm');
                this.sUserModalRef(component, sUser);
            });
        } else {
            return this.sUserModalRef(component, new SUser());
        }
    }

    sUserModalRef(component: Component, sUser: SUser): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sUser = sUser;
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
