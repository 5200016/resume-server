import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { BAuthor } from './b-author.model';
import { BAuthorPopupService } from './b-author-popup.service';
import { BAuthorService } from './b-author.service';

@Component({
    selector: 'jhi-b-author-dialog',
    templateUrl: './b-author-dialog.component.html'
})
export class BAuthorDialogComponent implements OnInit {

    bAuthor: BAuthor;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private bAuthorService: BAuthorService,
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

    setFileData(event, bAuthor, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                bAuthor[field] = base64Data;
                bAuthor[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bAuthor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bAuthorService.update(this.bAuthor), false);
        } else {
            this.subscribeToSaveResponse(
                this.bAuthorService.create(this.bAuthor), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BAuthor>, isCreated: boolean) {
        result.subscribe((res: BAuthor) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BAuthor, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.bAuthor.created'
            : 'resumeApp.bAuthor.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'bAuthorListModification', content: 'OK'});
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
    selector: 'jhi-b-author-popup',
    template: ''
})
export class BAuthorPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bAuthorPopupService: BAuthorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bAuthorPopupService
                    .open(BAuthorDialogComponent, params['id']);
            } else {
                this.modalRef = this.bAuthorPopupService
                    .open(BAuthorDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
