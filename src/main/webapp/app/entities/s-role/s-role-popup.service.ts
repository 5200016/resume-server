import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { SRole } from './s-role.model';
import { SRoleService } from './s-role.service';

@Injectable()
export class SRolePopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private sRoleService: SRoleService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.sRoleService.find(id).subscribe((sRole) => {
                sRole.createTime = this.datePipe
                    .transform(sRole.createTime, 'yyyy-MM-ddThh:mm');
                sRole.updateTime = this.datePipe
                    .transform(sRole.updateTime, 'yyyy-MM-ddThh:mm');
                this.sRoleModalRef(component, sRole);
            });
        } else {
            return this.sRoleModalRef(component, new SRole());
        }
    }

    sRoleModalRef(component: Component, sRole: SRole): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sRole = sRole;
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
