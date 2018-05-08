import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { BWorkProject } from './b-work-project.model';
import { BWorkProjectPopupService } from './b-work-project-popup.service';
import { BWorkProjectService } from './b-work-project.service';

@Component({
    selector: 'jhi-b-work-project-delete-dialog',
    templateUrl: './b-work-project-delete-dialog.component.html'
})
export class BWorkProjectDeleteDialogComponent {

    bWorkProject: BWorkProject;

    constructor(
        private bWorkProjectService: BWorkProjectService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bWorkProjectService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bWorkProjectListModification',
                content: 'Deleted an bWorkProject'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('resumeApp.bWorkProject.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-b-work-project-delete-popup',
    template: ''
})
export class BWorkProjectDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bWorkProjectPopupService: BWorkProjectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bWorkProjectPopupService
                .open(BWorkProjectDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
