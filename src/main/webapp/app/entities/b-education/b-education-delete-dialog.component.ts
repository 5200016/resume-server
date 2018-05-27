import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { BEducation } from './b-education.model';
import { BEducationPopupService } from './b-education-popup.service';
import { BEducationService } from './b-education.service';

@Component({
    selector: 'jhi-b-education-delete-dialog',
    templateUrl: './b-education-delete-dialog.component.html'
})
export class BEducationDeleteDialogComponent {

    bEducation: BEducation;

    constructor(
        private bEducationService: BEducationService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bEducationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bEducationListModification',
                content: 'Deleted an bEducation'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.bEducation.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-b-education-delete-popup',
    template: ''
})
export class BEducationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bEducationPopupService: BEducationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bEducationPopupService
                .open(BEducationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
