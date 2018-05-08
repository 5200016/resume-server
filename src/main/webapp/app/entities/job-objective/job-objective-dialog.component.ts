import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { JobObjective } from './job-objective.model';
import { JobObjectivePopupService } from './job-objective-popup.service';
import { JobObjectiveService } from './job-objective.service';

@Component({
    selector: 'jhi-job-objective-dialog',
    templateUrl: './job-objective-dialog.component.html'
})
export class JobObjectiveDialogComponent implements OnInit {

    jobObjective: JobObjective;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private jobObjectiveService: JobObjectiveService,
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
        if (this.jobObjective.id !== undefined) {
            this.subscribeToSaveResponse(
                this.jobObjectiveService.update(this.jobObjective), false);
        } else {
            this.subscribeToSaveResponse(
                this.jobObjectiveService.create(this.jobObjective), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<JobObjective>, isCreated: boolean) {
        result.subscribe((res: JobObjective) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: JobObjective, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.jobObjective.created'
            : 'resumeApp.jobObjective.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'jobObjectiveListModification', content: 'OK'});
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
    selector: 'jhi-job-objective-popup',
    template: ''
})
export class JobObjectivePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobObjectivePopupService: JobObjectivePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.jobObjectivePopupService
                    .open(JobObjectiveDialogComponent, params['id']);
            } else {
                this.modalRef = this.jobObjectivePopupService
                    .open(JobObjectiveDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
