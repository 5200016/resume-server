import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { BHonour } from './b-honour.model';
import { BHonourPopupService } from './b-honour-popup.service';
import { BHonourService } from './b-honour.service';

@Component({
    selector: 'jhi-b-honour-dialog',
    templateUrl: './b-honour-dialog.component.html'
})
export class BHonourDialogComponent implements OnInit {

    bHonour: BHonour;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private bHonourService: BHonourService,
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

    setFileData(event, bHonour, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                bHonour[field] = base64Data;
                bHonour[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bHonour.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bHonourService.update(this.bHonour), false);
        } else {
            this.subscribeToSaveResponse(
                this.bHonourService.create(this.bHonour), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BHonour>, isCreated: boolean) {
        result.subscribe((res: BHonour) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BHonour, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.bHonour.created'
            : 'resumeApp.bHonour.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'bHonourListModification', content: 'OK'});
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
    selector: 'jhi-b-honour-popup',
    template: ''
})
export class BHonourPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bHonourPopupService: BHonourPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bHonourPopupService
                    .open(BHonourDialogComponent, params['id']);
            } else {
                this.modalRef = this.bHonourPopupService
                    .open(BHonourDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
