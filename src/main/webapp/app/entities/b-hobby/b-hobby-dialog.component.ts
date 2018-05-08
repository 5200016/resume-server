import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { BHobby } from './b-hobby.model';
import { BHobbyPopupService } from './b-hobby-popup.service';
import { BHobbyService } from './b-hobby.service';

@Component({
    selector: 'jhi-b-hobby-dialog',
    templateUrl: './b-hobby-dialog.component.html'
})
export class BHobbyDialogComponent implements OnInit {

    bHobby: BHobby;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private bHobbyService: BHobbyService,
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

    setFileData(event, bHobby, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                bHobby[field] = base64Data;
                bHobby[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bHobby.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bHobbyService.update(this.bHobby), false);
        } else {
            this.subscribeToSaveResponse(
                this.bHobbyService.create(this.bHobby), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BHobby>, isCreated: boolean) {
        result.subscribe((res: BHobby) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BHobby, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.bHobby.created'
            : 'resumeApp.bHobby.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'bHobbyListModification', content: 'OK'});
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
    selector: 'jhi-b-hobby-popup',
    template: ''
})
export class BHobbyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bHobbyPopupService: BHobbyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bHobbyPopupService
                    .open(BHobbyDialogComponent, params['id']);
            } else {
                this.modalRef = this.bHobbyPopupService
                    .open(BHobbyDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
