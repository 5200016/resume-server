import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { BTemplateClassify } from './b-template-classify.model';
import { BTemplateClassifyPopupService } from './b-template-classify-popup.service';
import { BTemplateClassifyService } from './b-template-classify.service';

@Component({
    selector: 'jhi-b-template-classify-delete-dialog',
    templateUrl: './b-template-classify-delete-dialog.component.html'
})
export class BTemplateClassifyDeleteDialogComponent {

    bTemplateClassify: BTemplateClassify;

    constructor(
        private bTemplateClassifyService: BTemplateClassifyService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bTemplateClassifyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bTemplateClassifyListModification',
                content: 'Deleted an bTemplateClassify'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.bTemplateClassify.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-b-template-classify-delete-popup',
    template: ''
})
export class BTemplateClassifyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bTemplateClassifyPopupService: BTemplateClassifyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bTemplateClassifyPopupService
                .open(BTemplateClassifyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
