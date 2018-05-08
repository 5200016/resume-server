import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { JobObjective } from './job-objective.model';
import { JobObjectivePopupService } from './job-objective-popup.service';
import { JobObjectiveService } from './job-objective.service';

@Component({
    selector: 'jhi-job-objective-delete-dialog',
    templateUrl: './job-objective-delete-dialog.component.html'
})
export class JobObjectiveDeleteDialogComponent {

    jobObjective: JobObjective;

    constructor(
        private jobObjectiveService: JobObjectiveService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jobObjectiveService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'jobObjectiveListModification',
                content: 'Deleted an jobObjective'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.jobObjective.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-job-objective-delete-popup',
    template: ''
})
export class JobObjectiveDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobObjectivePopupService: JobObjectivePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.jobObjectivePopupService
                .open(JobObjectiveDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
