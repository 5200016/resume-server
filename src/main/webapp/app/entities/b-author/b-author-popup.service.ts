import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BAuthor } from './b-author.model';
import { BAuthorService } from './b-author.service';

@Injectable()
export class BAuthorPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bAuthorService: BAuthorService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bAuthorService.find(id).subscribe((bAuthor) => {
                bAuthor.createTime = this.datePipe
                    .transform(bAuthor.createTime, 'yyyy-MM-ddThh:mm');
                bAuthor.updateTime = this.datePipe
                    .transform(bAuthor.updateTime, 'yyyy-MM-ddThh:mm');
                this.bAuthorModalRef(component, bAuthor);
            });
        } else {
            return this.bAuthorModalRef(component, new BAuthor());
        }
    }

    bAuthorModalRef(component: Component, bAuthor: BAuthor): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bAuthor = bAuthor;
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
