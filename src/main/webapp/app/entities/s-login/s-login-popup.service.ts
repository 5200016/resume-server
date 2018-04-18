import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { SLogin } from './s-login.model';
import { SLoginService } from './s-login.service';

@Injectable()
export class SLoginPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private sLoginService: SLoginService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.sLoginService.find(id).subscribe((sLogin) => {
                sLogin.createTime = this.datePipe
                    .transform(sLogin.createTime, 'yyyy-MM-ddThh:mm');
                sLogin.updateTime = this.datePipe
                    .transform(sLogin.updateTime, 'yyyy-MM-ddThh:mm');
                this.sLoginModalRef(component, sLogin);
            });
        } else {
            return this.sLoginModalRef(component, new SLogin());
        }
    }

    sLoginModalRef(component: Component, sLogin: SLogin): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sLogin = sLogin;
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
