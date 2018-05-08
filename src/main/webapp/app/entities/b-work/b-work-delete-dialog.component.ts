import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { BWork } from './b-work.model';
import { BWorkPopupService } from './b-work-popup.service';
import { BWorkService } from './b-work.service';

@Component({
    selector: 'jhi-b-work-delete-dialog',
    templateUrl: './b-work-delete-dialog.component.html'
})
export class BWorkDeleteDialogComponent {

    bWork: BWork;

    constructor(
        private bWorkService: BWorkService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bWorkService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bWorkListModification',
                content: 'Deleted an bWork'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.bWork.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-b-work-delete-popup',
    template: ''
})
export class BWorkDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bWorkPopupService: BWorkPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bWorkPopupService
                .open(BWorkDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
