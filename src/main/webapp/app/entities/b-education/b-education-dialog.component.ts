import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { BEducation } from './b-education.model';
import { BEducationPopupService } from './b-education-popup.service';
import { BEducationService } from './b-education.service';

@Component({
    selector: 'jhi-b-education-dialog',
    templateUrl: './b-education-dialog.component.html'
})
export class BEducationDialogComponent implements OnInit {

    bEducation: BEducation;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private bEducationService: BEducationService,
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

    setFileData(event, bEducation, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                bEducation[field] = base64Data;
                bEducation[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bEducation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bEducationService.update(this.bEducation), false);
        } else {
            this.subscribeToSaveResponse(
                this.bEducationService.create(this.bEducation), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BEducation>, isCreated: boolean) {
        result.subscribe((res: BEducation) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BEducation, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.bEducation.created'
            : 'resumeApp.bEducation.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'bEducationListModification', content: 'OK'});
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
    selector: 'jhi-b-education-popup',
    template: ''
})
export class BEducationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bEducationPopupService: BEducationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bEducationPopupService
                    .open(BEducationDialogComponent, params['id']);
            } else {
                this.modalRef = this.bEducationPopupService
                    .open(BEducationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
