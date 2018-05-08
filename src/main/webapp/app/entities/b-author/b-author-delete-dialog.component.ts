import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { BAuthor } from './b-author.model';
import { BAuthorPopupService } from './b-author-popup.service';
import { BAuthorService } from './b-author.service';

@Component({
    selector: 'jhi-b-author-delete-dialog',
    templateUrl: './b-author-delete-dialog.component.html'
})
export class BAuthorDeleteDialogComponent {

    bAuthor: BAuthor;

    constructor(
        private bAuthorService: BAuthorService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bAuthorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bAuthorListModification',
                content: 'Deleted an bAuthor'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.bAuthor.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-b-author-delete-popup',
    template: ''
})
export class BAuthorDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bAuthorPopupService: BAuthorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bAuthorPopupService
                .open(BAuthorDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
