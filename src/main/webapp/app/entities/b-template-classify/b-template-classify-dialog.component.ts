import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BTemplateClassify } from './b-template-classify.model';
import { BTemplateClassifyPopupService } from './b-template-classify-popup.service';
import { BTemplateClassifyService } from './b-template-classify.service';
import { BTemplate, BTemplateService } from '../b-template';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-b-template-classify-dialog',
    templateUrl: './b-template-classify-dialog.component.html'
})
export class BTemplateClassifyDialogComponent implements OnInit {

    bTemplateClassify: BTemplateClassify;
    authorities: any[];
    isSaving: boolean;

    btemplates: BTemplate[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private bTemplateClassifyService: BTemplateClassifyService,
        private bTemplateService: BTemplateService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.bTemplateService.query()
            .subscribe((res: ResponseWrapper) => { this.btemplates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bTemplateClassify.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bTemplateClassifyService.update(this.bTemplateClassify), false);
        } else {
            this.subscribeToSaveResponse(
                this.bTemplateClassifyService.create(this.bTemplateClassify), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BTemplateClassify>, isCreated: boolean) {
        result.subscribe((res: BTemplateClassify) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BTemplateClassify, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.bTemplateClassify.created'
            : 'resumeApp.bTemplateClassify.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'bTemplateClassifyListModification', content: 'OK'});
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

    trackBTemplateById(index: number, item: BTemplate) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-b-template-classify-popup',
    template: ''
})
export class BTemplateClassifyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bTemplateClassifyPopupService: BTemplateClassifyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bTemplateClassifyPopupService
                    .open(BTemplateClassifyDialogComponent, params['id']);
            } else {
                this.modalRef = this.bTemplateClassifyPopupService
                    .open(BTemplateClassifyDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
