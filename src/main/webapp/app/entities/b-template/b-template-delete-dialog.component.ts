import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { BTemplate } from './b-template.model';
import { BTemplatePopupService } from './b-template-popup.service';
import { BTemplateService } from './b-template.service';

@Component({
    selector: 'jhi-b-template-delete-dialog',
    templateUrl: './b-template-delete-dialog.component.html'
})
export class BTemplateDeleteDialogComponent {

    bTemplate: BTemplate;

    constructor(
        private bTemplateService: BTemplateService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bTemplateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bTemplateListModification',
                content: 'Deleted an bTemplate'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.bTemplate.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-b-template-delete-popup',
    template: ''
})
export class BTemplateDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bTemplatePopupService: BTemplatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bTemplatePopupService
                .open(BTemplateDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
