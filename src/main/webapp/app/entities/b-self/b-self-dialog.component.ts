import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { BSelf } from './b-self.model';
import { BSelfPopupService } from './b-self-popup.service';
import { BSelfService } from './b-self.service';

@Component({
    selector: 'jhi-b-self-dialog',
    templateUrl: './b-self-dialog.component.html'
})
export class BSelfDialogComponent implements OnInit {

    bSelf: BSelf;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private bSelfService: BSelfService,
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

    setFileData(event, bSelf, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                bSelf[field] = base64Data;
                bSelf[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bSelf.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bSelfService.update(this.bSelf), false);
        } else {
            this.subscribeToSaveResponse(
                this.bSelfService.create(this.bSelf), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BSelf>, isCreated: boolean) {
        result.subscribe((res: BSelf) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BSelf, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.bSelf.created'
            : 'resumeApp.bSelf.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'bSelfListModification', content: 'OK'});
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
    selector: 'jhi-b-self-popup',
    template: ''
})
export class BSelfPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bSelfPopupService: BSelfPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bSelfPopupService
                    .open(BSelfDialogComponent, params['id']);
            } else {
                this.modalRef = this.bSelfPopupService
                    .open(BSelfDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
