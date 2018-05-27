import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { BSelf } from './b-self.model';
import { BSelfPopupService } from './b-self-popup.service';
import { BSelfService } from './b-self.service';

@Component({
    selector: 'jhi-b-self-delete-dialog',
    templateUrl: './b-self-delete-dialog.component.html'
})
export class BSelfDeleteDialogComponent {

    bSelf: BSelf;

    constructor(
        private bSelfService: BSelfService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bSelfService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bSelfListModification',
                content: 'Deleted an bSelf'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.bSelf.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-b-self-delete-popup',
    template: ''
})
export class BSelfDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bSelfPopupService: BSelfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bSelfPopupService
                .open(BSelfDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
