import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BResume } from './b-resume.model';
import { BResumePopupService } from './b-resume-popup.service';
import { BResumeService } from './b-resume.service';

@Component({
    selector: 'jhi-b-resume-dialog',
    templateUrl: './b-resume-dialog.component.html'
})
export class BResumeDialogComponent implements OnInit {

    bResume: BResume;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private bResumeService: BResumeService,
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
        if (this.bResume.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bResumeService.update(this.bResume), false);
        } else {
            this.subscribeToSaveResponse(
                this.bResumeService.create(this.bResume), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BResume>, isCreated: boolean) {
        result.subscribe((res: BResume) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BResume, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.bResume.created'
            : 'resumeApp.bResume.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'bResumeListModification', content: 'OK'});
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
    selector: 'jhi-b-resume-popup',
    template: ''
})
export class BResumePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bResumePopupService: BResumePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bResumePopupService
                    .open(BResumeDialogComponent, params['id']);
            } else {
                this.modalRef = this.bResumePopupService
                    .open(BResumeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
