import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { BContact } from './b-contact.model';
import { BContactPopupService } from './b-contact-popup.service';
import { BContactService } from './b-contact.service';

@Component({
    selector: 'jhi-b-contact-delete-dialog',
    templateUrl: './b-contact-delete-dialog.component.html'
})
export class BContactDeleteDialogComponent {

    bContact: BContact;

    constructor(
        private bContactService: BContactService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bContactService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bContactListModification',
                content: 'Deleted an bContact'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.bContact.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-b-contact-delete-popup',
    template: ''
})
export class BContactDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bContactPopupService: BContactPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bContactPopupService
                .open(BContactDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
