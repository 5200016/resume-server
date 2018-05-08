import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { BHonour } from './b-honour.model';
import { BHonourPopupService } from './b-honour-popup.service';
import { BHonourService } from './b-honour.service';

@Component({
    selector: 'jhi-b-honour-delete-dialog',
    templateUrl: './b-honour-delete-dialog.component.html'
})
export class BHonourDeleteDialogComponent {

    bHonour: BHonour;

    constructor(
        private bHonourService: BHonourService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bHonourService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bHonourListModification',
                content: 'Deleted an bHonour'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.bHonour.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-b-honour-delete-popup',
    template: ''
})
export class BHonourDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bHonourPopupService: BHonourPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bHonourPopupService
                .open(BHonourDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
