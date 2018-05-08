import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BTemplate } from './b-template.model';
import { BTemplatePopupService } from './b-template-popup.service';
import { BTemplateService } from './b-template.service';
import { BResume, BResumeService } from '../b-resume';
import { BTemplateClassify, BTemplateClassifyService } from '../b-template-classify';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-b-template-dialog',
    templateUrl: './b-template-dialog.component.html'
})
export class BTemplateDialogComponent implements OnInit {

    bTemplate: BTemplate;
    authorities: any[];
    isSaving: boolean;

    resumes: BResume[];

    btemplateclassifies: BTemplateClassify[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private bTemplateService: BTemplateService,
        private bResumeService: BResumeService,
        private bTemplateClassifyService: BTemplateClassifyService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.bResumeService
            .query({filter: 'btemplate-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.bTemplate.resume || !this.bTemplate.resume.id) {
                    this.resumes = res.json;
                } else {
                    this.bResumeService
                        .find(this.bTemplate.resume.id)
                        .subscribe((subRes: BResume) => {
                            this.resumes = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.bTemplateClassifyService.query()
            .subscribe((res: ResponseWrapper) => { this.btemplateclassifies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bTemplate.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bTemplateService.update(this.bTemplate), false);
        } else {
            this.subscribeToSaveResponse(
                this.bTemplateService.create(this.bTemplate), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BTemplate>, isCreated: boolean) {
        result.subscribe((res: BTemplate) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BTemplate, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'resumeApp.bTemplate.created'
            : 'resumeApp.bTemplate.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'bTemplateListModification', content: 'OK'});
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

    trackBResumeById(index: number, item: BResume) {
        return item.id;
    }

    trackBTemplateClassifyById(index: number, item: BTemplateClassify) {
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
    selector: 'jhi-b-template-popup',
    template: ''
})
export class BTemplatePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bTemplatePopupService: BTemplatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bTemplatePopupService
                    .open(BTemplateDialogComponent, params['id']);
            } else {
                this.modalRef = this.bTemplatePopupService
                    .open(BTemplateDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
