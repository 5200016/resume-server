import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { BInformation } from './b-information.model';
import { BInformationPopupService } from './b-information-popup.service';
import { BInformationService } from './b-information.service';

@Component({
    selector: 'jhi-b-information-dialog',
    templateUrl: './b-information-dialog.component.html'
})
export class BInformationDialogComponent implements OnInit {

    bInformation: BInformation;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private bInformationService: BInformationService,
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

    setFileData(event, bInformation, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                bInformation[field] = base64Data;
                bInformation[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bInformation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bInformationService.update(this.bInformation), false);
        } else {
            this.subscribeToSaveResponse(
                this.bInformationService.create(this.bInformation), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BInformation>, isCreated: boolean) {
        result.subscribe((res: BInformation) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BInformation, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.bInformation.created'
            : 'resumeApp.bInformation.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'bInformationListModification', content: 'OK'});
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
    selector: 'jhi-b-information-popup',
    template: ''
})
export class BInformationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bInformationPopupService: BInformationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bInformationPopupService
                    .open(BInformationDialogComponent, params['id']);
            } else {
                this.modalRef = this.bInformationPopupService
                    .open(BInformationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
