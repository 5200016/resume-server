import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BWork } from './b-work.model';
import { BWorkPopupService } from './b-work-popup.service';
import { BWorkService } from './b-work.service';

@Component({
    selector: 'jhi-b-work-dialog',
    templateUrl: './b-work-dialog.component.html'
})
export class BWorkDialogComponent implements OnInit {

    bWork: BWork;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private bWorkService: BWorkService,
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
        if (this.bWork.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bWorkService.update(this.bWork), false);
        } else {
            this.subscribeToSaveResponse(
                this.bWorkService.create(this.bWork), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BWork>, isCreated: boolean) {
        result.subscribe((res: BWork) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BWork, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.bWork.created'
            : 'resumeApp.bWork.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'bWorkListModification', content: 'OK'});
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
    selector: 'jhi-b-work-popup',
    template: ''
})
export class BWorkPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bWorkPopupService: BWorkPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bWorkPopupService
                    .open(BWorkDialogComponent, params['id']);
            } else {
                this.modalRef = this.bWorkPopupService
                    .open(BWorkDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
