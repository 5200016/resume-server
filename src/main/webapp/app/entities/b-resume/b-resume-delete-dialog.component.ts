import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { BResume } from './b-resume.model';
import { BResumePopupService } from './b-resume-popup.service';
import { BResumeService } from './b-resume.service';

@Component({
    selector: 'jhi-b-resume-delete-dialog',
    templateUrl: './b-resume-delete-dialog.component.html'
})
export class BResumeDeleteDialogComponent {

    bResume: BResume;

    constructor(
        private bResumeService: BResumeService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bResumeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bResumeListModification',
                content: 'Deleted an bResume'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.bResume.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-b-resume-delete-popup',
    template: ''
})
export class BResumeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bResumePopupService: BResumePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bResumePopupService
                .open(BResumeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
