import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { BWorkProject } from './b-work-project.model';
import { BWorkProjectPopupService } from './b-work-project-popup.service';
import { BWorkProjectService } from './b-work-project.service';

@Component({
    selector: 'jhi-b-work-project-dialog',
    templateUrl: './b-work-project-dialog.component.html'
})
export class BWorkProjectDialogComponent implements OnInit {

    bWorkProject: BWorkProject;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private bWorkProjectService: BWorkProjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, bWorkProject, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                bWorkProject[field] = base64Data;
                bWorkProject[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bWorkProject.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bWorkProjectService.update(this.bWorkProject), false);
        } else {
            this.subscribeToSaveResponse(
                this.bWorkProjectService.create(this.bWorkProject), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BWorkProject>, isCreated: boolean) {
        result.subscribe((res: BWorkProject) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BWorkProject, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.bWorkProject.created'
            : 'resumeApp.bWorkProject.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'bWorkProjectListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-b-work-project-popup',
    template: ''
})
export class BWorkProjectPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bWorkProjectPopupService: BWorkProjectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bWorkProjectPopupService
                    .open(BWorkProjectDialogComponent, params['id']);
            } else {
                this.modalRef = this.bWorkProjectPopupService
                    .open(BWorkProjectDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
