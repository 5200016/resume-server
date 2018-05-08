import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BContact } from './b-contact.model';
import { BContactPopupService } from './b-contact-popup.service';
import { BContactService } from './b-contact.service';

@Component({
    selector: 'jhi-b-contact-dialog',
    templateUrl: './b-contact-dialog.component.html'
})
export class BContactDialogComponent implements OnInit {

    bContact: BContact;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private bContactService: BContactService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bContact.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bContactService.update(this.bContact), false);
        } else {
            this.subscribeToSaveResponse(
                this.bContactService.create(this.bContact), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BContact>, isCreated: boolean) {
        result.subscribe((res: BContact) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BContact, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.bContact.created'
            : 'resumeApp.bContact.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'bContactListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-b-contact-popup',
    template: ''
})
export class BContactPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bContactPopupService: BContactPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bContactPopupService
                    .open(BContactDialogComponent, params['id']);
            } else {
                this.modalRef = this.bContactPopupService
                    .open(BContactDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
