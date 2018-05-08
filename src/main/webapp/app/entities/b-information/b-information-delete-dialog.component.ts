import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { BInformation } from './b-information.model';
import { BInformationPopupService } from './b-information-popup.service';
import { BInformationService } from './b-information.service';

@Component({
    selector: 'jhi-b-information-delete-dialog',
    templateUrl: './b-information-delete-dialog.component.html'
})
export class BInformationDeleteDialogComponent {

    bInformation: BInformation;

    constructor(
        private bInformationService: BInformationService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bInformationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bInformationListModification',
                content: 'Deleted an bInformation'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.bInformation.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-b-information-delete-popup',
    template: ''
})
export class BInformationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bInformationPopupService: BInformationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bInformationPopupService
                .open(BInformationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
