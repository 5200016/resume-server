import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { BHobby } from './b-hobby.model';
import { BHobbyPopupService } from './b-hobby-popup.service';
import { BHobbyService } from './b-hobby.service';

@Component({
    selector: 'jhi-b-hobby-delete-dialog',
    templateUrl: './b-hobby-delete-dialog.component.html'
})
export class BHobbyDeleteDialogComponent {

    bHobby: BHobby;

    constructor(
        private bHobbyService: BHobbyService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bHobbyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bHobbyListModification',
                content: 'Deleted an bHobby'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.bHobby.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-b-hobby-delete-popup',
    template: ''
})
export class BHobbyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bHobbyPopupService: BHobbyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bHobbyPopupService
                .open(BHobbyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
